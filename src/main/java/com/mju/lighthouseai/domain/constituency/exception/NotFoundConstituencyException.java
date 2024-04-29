package com.mju.lighthouseai.domain.constituency.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class NotFoundConstituencyException extends CustomException {

    public NotFoundConstituencyException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
