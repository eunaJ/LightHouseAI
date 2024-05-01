package com.mju.lighthouseai.domain.other_service.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class NotFoundOtherServiceException extends CustomException {

    public NotFoundOtherServiceException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
