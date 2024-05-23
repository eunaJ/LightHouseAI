package com.mju.lighthouseai.domain.like.exception;

import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class NotFoundLikeException extends CustomException {
    public NotFoundLikeException(final ErrorCode errorCode) {
        super(errorCode);
    }
}