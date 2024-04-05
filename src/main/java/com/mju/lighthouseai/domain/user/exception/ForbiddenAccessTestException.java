package com.mju.lighthouseai.domain.user.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class ForbiddenAccessTestException extends CustomException {

    public ForbiddenAccessTestException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
