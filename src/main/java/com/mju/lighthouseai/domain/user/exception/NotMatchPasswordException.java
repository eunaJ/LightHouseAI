package com.mju.lighthouseai.domain.user.exception;

import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class NotMatchPasswordException extends CustomException {
    public NotMatchPasswordException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
