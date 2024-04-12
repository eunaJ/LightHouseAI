package com.mju.lighthouseai.domain.cafe.exceoption;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class NotFoundCafeException extends CustomException {

    public NotFoundCafeException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
