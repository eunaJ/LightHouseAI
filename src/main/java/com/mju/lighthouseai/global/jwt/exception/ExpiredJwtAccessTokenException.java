package com.mju.lighthouseai.global.jwt.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class ExpiredJwtAccessTokenException extends CustomException {
    public ExpiredJwtAccessTokenException(final ErrorCode errorCode) {
        super(errorCode);
    }
}