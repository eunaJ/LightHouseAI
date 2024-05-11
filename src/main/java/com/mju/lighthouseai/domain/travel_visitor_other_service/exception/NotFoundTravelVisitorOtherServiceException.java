package com.mju.lighthouseai.domain.travel_visitor_other_service.exception;

import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class NotFoundTravelVisitorOtherServiceException extends CustomException {
    public NotFoundTravelVisitorOtherServiceException(final ErrorCode errorCode) {
        super(errorCode);
    }
}