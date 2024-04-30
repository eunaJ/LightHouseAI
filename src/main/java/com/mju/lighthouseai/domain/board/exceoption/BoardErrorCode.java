package com.mju.lighthouseai.domain.board.exceoption;

import com.mju.lighthouseai.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardErrorCode implements ErrorCode {


    // 403
    FORBIDDEN_ACCESS_Board(HttpStatus.FORBIDDEN, "게시물에 접근할 수 없습니다."),

    // 404
    NOT_FOUND_Board(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
