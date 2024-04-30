package com.mju.lighthouseai.domain.shoppingmall.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class AlreadyExistsShoppingMallListException extends CustomException {

    public AlreadyExistsShoppingMallListException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
