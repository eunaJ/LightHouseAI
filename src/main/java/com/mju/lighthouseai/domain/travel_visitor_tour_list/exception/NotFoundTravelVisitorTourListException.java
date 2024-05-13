package com.mju.lighthouseai.domain.travel_visitor_tour_list.exception;

import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class NotFoundTravelVisitorTourListException extends CustomException {
    public NotFoundTravelVisitorTourListException(final ErrorCode errorCode) {
        super(errorCode);
    }
}