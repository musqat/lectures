package com.example.Store.exception;

import com.example.Store.type.ErrorCode;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorMessage;

    public ApiException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }

}
