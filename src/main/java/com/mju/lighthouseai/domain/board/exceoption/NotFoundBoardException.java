package com.mju.lighthouseai.domain.board.exceoption;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class NotFoundBoardException extends CustomException {

    public NotFoundBoardException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
