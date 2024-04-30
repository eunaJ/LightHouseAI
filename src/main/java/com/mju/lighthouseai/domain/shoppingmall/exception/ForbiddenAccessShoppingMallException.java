package com.mju.lighthouseai.domain.shoppingmall.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class ForbiddenAccessShoppingMallException extends CustomException {

    public ForbiddenAccessShoppingMallException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
