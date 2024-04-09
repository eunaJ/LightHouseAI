package com.mju.lighthouseai.domain.user.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class NotFoundUserException extends CustomException {

    public NotFoundUserException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
