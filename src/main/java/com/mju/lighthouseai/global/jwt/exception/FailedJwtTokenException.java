package com.mju.lighthouseai.global.jwt.exception;

import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class FailedJwtTokenException extends CustomException {
    public FailedJwtTokenException(final ErrorCode errorCode) {
        super(errorCode);
    }
}