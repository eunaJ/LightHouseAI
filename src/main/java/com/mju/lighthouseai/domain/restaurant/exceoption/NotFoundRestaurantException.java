package com.mju.lighthouseai.domain.restaurant.exceoption;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class NotFoundRestaurantException extends CustomException {

    public NotFoundRestaurantException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
