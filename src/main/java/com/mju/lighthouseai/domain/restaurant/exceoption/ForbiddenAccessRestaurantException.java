package com.mju.lighthouseai.domain.restaurant.exceoption;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class ForbiddenAccessRestaurantException extends CustomException {

    public ForbiddenAccessRestaurantException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
