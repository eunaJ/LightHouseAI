package com.mju.lighthouseai.domain.review.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class NotFoundReviewException extends CustomException {

    public NotFoundReviewException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
