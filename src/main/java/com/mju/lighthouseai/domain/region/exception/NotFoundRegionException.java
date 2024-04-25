package com.mju.lighthouseai.domain.region.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class NotFoundRegionException extends CustomException {

    public NotFoundRegionException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
