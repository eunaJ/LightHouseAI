package com.mju.lighthouseai.domain.like.exception;

import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class AlreadyExistLikeException extends CustomException {
    public AlreadyExistLikeException(final ErrorCode errorCode)  {
        super(errorCode);
    }
}