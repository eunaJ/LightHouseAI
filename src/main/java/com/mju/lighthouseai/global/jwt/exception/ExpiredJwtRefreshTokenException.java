package com.mju.lighthouseai.global.jwt.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class ExpiredJwtRefreshTokenException extends CustomException {
    public ExpiredJwtRefreshTokenException(final ErrorCode errorCode) {
        super(errorCode);
    }
}