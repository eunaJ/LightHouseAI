package com.mju.lighthouseai.domain.user.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class AlreadyExistsEmailException extends CustomException {

    public AlreadyExistsEmailException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
