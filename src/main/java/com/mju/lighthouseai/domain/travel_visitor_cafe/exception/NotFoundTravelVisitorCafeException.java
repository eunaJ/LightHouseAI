package com.mju.lighthouseai.domain.travel_visitor_cafe.exception;

import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class NotFoundTravelVisitorCafeException extends CustomException {
    public NotFoundTravelVisitorCafeException(final ErrorCode errorCode) {
        super(errorCode);
    }
}