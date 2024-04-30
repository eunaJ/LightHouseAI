package com.mju.lighthouseai.domain.tour_list.exceoption;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class ForbiddenAccessTourListException extends CustomException {

    public ForbiddenAccessTourListException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
