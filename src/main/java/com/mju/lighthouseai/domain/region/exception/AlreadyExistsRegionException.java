package com.mju.lighthouseai.domain.region.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class AlreadyExistsRegionException extends CustomException {
    public AlreadyExistsRegionException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
