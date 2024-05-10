package com.mju.lighthouseai.domain.travel_visitor_restaurant.exception;

import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class NotFoundTravelVisitorRestaurant extends CustomException {
    public NotFoundTravelVisitorRestaurant(final ErrorCode errorCode) {
        super(errorCode);
    }
}