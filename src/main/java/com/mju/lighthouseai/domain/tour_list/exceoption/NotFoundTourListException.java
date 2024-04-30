package com.mju.lighthouseai.domain.tour_list.exceoption;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class NotFoundTourListException extends CustomException {

    public NotFoundTourListException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
