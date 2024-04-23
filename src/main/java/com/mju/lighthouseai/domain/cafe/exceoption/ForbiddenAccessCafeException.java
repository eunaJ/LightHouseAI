package com.mju.lighthouseai.domain.cafe.exceoption;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class ForbiddenAccessCafeException extends CustomException {

    public ForbiddenAccessCafeException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
