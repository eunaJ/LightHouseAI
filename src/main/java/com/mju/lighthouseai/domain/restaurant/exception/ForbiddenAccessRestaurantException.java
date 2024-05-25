package com.mju.lighthouseai.domain.restaurant.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class ForbiddenAccessRestaurantException extends CustomException {

    public ForbiddenAccessRestaurantException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
