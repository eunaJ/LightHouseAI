package com.mju.lighthouseai.domain.user.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class ForbiddenAccessException extends CustomException {

    public ForbiddenAccessException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
