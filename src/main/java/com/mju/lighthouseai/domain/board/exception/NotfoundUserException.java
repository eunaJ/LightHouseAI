package com.mju.lighthouseai.domain.board.exception;

import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class NotfoundUserException  extends CustomException {
    public NotfoundUserException (final ErrorCode errorCode) {
        super(errorCode);
    }

}
