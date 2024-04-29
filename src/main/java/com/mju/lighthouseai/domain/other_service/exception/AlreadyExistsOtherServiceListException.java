package com.mju.lighthouseai.domain.other_service.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class AlreadyExistsOtherServiceListException extends CustomException {

    public AlreadyExistsOtherServiceListException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
