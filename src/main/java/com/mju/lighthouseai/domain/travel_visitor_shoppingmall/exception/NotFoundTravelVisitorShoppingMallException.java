package com.mju.lighthouseai.domain.travel_visitor_shoppingmall.exception;

import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class NotFoundTravelVisitorShoppingMallException extends CustomException {
    public NotFoundTravelVisitorShoppingMallException(final ErrorCode errorCode) {
        super(errorCode);
    }
}