package com.example.Store.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND("유저를 찾지 못했습니다."),
    STORE_NOT_FOUND("가게를 찾지 못했습니다."),
    USERNAME_ALREADY_EXISTS("닉네임이 이미 존재합니다."),
    EMAIL_ALREADY_EXISTS("이메일이 이미 존재합니다."),
    RESERVATION_NOT_FOUND("예약을 찾지 못했습니다."),
    RESERVATION_TIME_OUT("예약후 10분이 지났습니다."),
    INVALID_REQUEST("Invalid request"),
    INTERNAL_SERVER_ERROR("Internal server error");

    private final String description;
}
