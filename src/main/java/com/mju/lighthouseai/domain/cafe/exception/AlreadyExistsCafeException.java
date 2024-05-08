package com.mju.lighthouseai.domain.cafe.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class AlreadyExistsCafeException extends CustomException {

    public AlreadyExistsCafeException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
