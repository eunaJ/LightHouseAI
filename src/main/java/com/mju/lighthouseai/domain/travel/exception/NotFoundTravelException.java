package com.mju.lighthouseai.domain.travel.exception;

import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class NotFoundTravelException extends CustomException {
    public NotFoundTravelException(final ErrorCode errorCode) {
        super(errorCode);
    }
}