package com.mju.lighthouseai.domain.board.exceoption;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class ForbiddenAccessBoardException extends CustomException {

    public ForbiddenAccessBoardException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
