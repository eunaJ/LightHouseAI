package com.mju.lighthouseai.domain.board.exception;

import com.mju.lighthouseai.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardLikeErrorCode implements ErrorCode {


    // 403
    NOT_FOUND_User(HttpStatus.FORBIDDEN, "사용자를 찾을 수 없습니다."),
    // 404
    NOT_FOUND_Board(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
