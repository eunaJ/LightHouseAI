package com.mju.lighthouseai.domain.restaurant.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class NotFoundRestaurantException extends CustomException {

    public NotFoundRestaurantException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
