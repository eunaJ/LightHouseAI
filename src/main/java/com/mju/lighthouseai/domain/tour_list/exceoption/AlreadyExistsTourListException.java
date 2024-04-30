package com.mju.lighthouseai.domain.tour_list.exceoption;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class AlreadyExistsTourListException extends CustomException {

    public AlreadyExistsTourListException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
