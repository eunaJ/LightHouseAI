package com.mju.lighthouseai.domain.restaurant.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class AlreadyExistsRestaurantListException extends CustomException {

    public AlreadyExistsRestaurantListException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
