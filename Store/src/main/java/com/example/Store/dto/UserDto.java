package com.example.Store.dto;

import com.example.Store.entity.User;
import com.example.Store.type.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private long id;
    private String userName;
    private String password;
    private String email;
    private String role;

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    public static User toEntity(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .userName(dto.getUserName())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .role(Role.valueOf(dto.getRole()))
                .build();
    }
}
