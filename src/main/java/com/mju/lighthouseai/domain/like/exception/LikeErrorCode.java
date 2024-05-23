package com.mju.lighthouseai.domain.like.exception;

import com.mju.lighthouseai.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LikeErrorCode implements ErrorCode {
    // 400
    ALREADY_EXIST_Like(HttpStatus.BAD_REQUEST, "이미 좋아요가 존재합니다."),

    // 404
    NOT_FOUND_User(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    NOT_FOUND_Board(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."),
    Not_Found_Like(HttpStatus.NOT_FOUND, "좋아요를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
