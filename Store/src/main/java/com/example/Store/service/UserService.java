package com.example.Store.service;

import com.example.Store.dto.UserDto;
import com.example.Store.entity.User;
import com.example.Store.exception.ApiException;
import com.example.Store.repository.UserRepository;
import com.example.Store.security.TokenProvider;
import com.example.Store.type.ErrorCode;
import com.example.Store.type.Role;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 사용자 등록, 인증 및 사용자 정보 조회 등의 기능을 제공합니다.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenProvider tokenProvider;

    /**
     * 새로운 사용자를 등록합니다.
     */
    @Transactional
    public UserDto registerUser(UserDto userDto) {
        validateUser(userDto.getUserName(), userDto.getEmail());

        User user = User.builder()
                .userName(userDto.getUserName())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .role(Role.valueOf(userDto.getRole()))
                .build();

        return UserDto.fromEntity(userRepository.save(user));
    }

    /**
     * 사용자 이름과 비밀번호를 기반으로 사용자를 인증합니다.
     */
    public Optional<UserDto> authenticateUser(String userName, String password) {
        Optional<User> userOpt = userRepository.findByUserName(userName);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return Optional.of(UserDto.fromEntity(userOpt.get()));
        }
        return Optional.empty();
    }

    /**
     * 사용자 이름과 이메일의 유효성을 검사합니다.
     */
    private void validateUser(String userName, String email) {
        if (userRepository.existsByUserName(userName)) {
            throw new ApiException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(email)) {
            throw new ApiException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    /**
     * 요청에서 JWT 토큰을 사용하여 사용자 ID를 추출합니다.
     */
    public Long getUserIdFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String userName = tokenProvider.getUsername(token);
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
        return user.getId();
    }

    /**
     * 사용자 이름으로 사용자를 로드합니다.
     */
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userName));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();
    }
}
