package com.mju.lighthouseai.domain.user.exception;

import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class MatchCurrentPasswordException extends CustomException {
    public MatchCurrentPasswordException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
