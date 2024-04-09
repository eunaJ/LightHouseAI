package com.mju.lighthouseai.domain.user.exception;

import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class DuplicateNicknameException extends CustomException {
    public DuplicateNicknameException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
