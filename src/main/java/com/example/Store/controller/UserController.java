package com.example.Store.controller;

import com.example.Store.dto.UserDto;
import com.example.Store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 새로운 사용자를 등록
     */
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        UserDto registeredUser = userService.registerUser(userDto);
        return ResponseEntity.ok(registeredUser);
    }

    /**
     * 사용자를 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody UserDto userDto) {
        return userService.authenticateUser(userDto.getUserName(), userDto.getPassword())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }
}
