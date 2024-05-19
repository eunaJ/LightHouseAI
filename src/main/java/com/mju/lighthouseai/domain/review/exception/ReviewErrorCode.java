package com.mju.lighthouseai.domain.review.exception;

import com.mju.lighthouseai.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements ErrorCode {

    // 400
    ALREADY_EXIST_Review(HttpStatus.BAD_REQUEST, "이미 리뷰가 존재합니다."),

    // 403
    FORBIDDEN_ACCESS_Review(HttpStatus.FORBIDDEN, "리뷰에 접근할 수 없습니다."),

    // 404
    NOT_FOUND_Review(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
