package com.mju.lighthouseai.domain.review.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class AlreadyExistsReviewException extends CustomException {

    public AlreadyExistsReviewException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
