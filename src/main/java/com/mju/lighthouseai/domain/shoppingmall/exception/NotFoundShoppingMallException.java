package com.mju.lighthouseai.domain.shoppingmall.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class NotFoundShoppingMallException extends CustomException {

    public NotFoundShoppingMallException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
