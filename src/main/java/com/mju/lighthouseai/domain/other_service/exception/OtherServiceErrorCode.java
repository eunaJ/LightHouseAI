package com.mju.lighthouseai.domain.other_service.exception;

import com.mju.lighthouseai.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OtherServiceErrorCode implements ErrorCode {

    // 400
    ALREADY_EXIST_OtherService(HttpStatus.BAD_REQUEST, "기타 서비스가 존재합니다."),

    // 403
    FORBIDDEN_ACCESS_OtherService(HttpStatus.FORBIDDEN, "기타 서비스에 접근할 수 없습니다."),

    // 404
    NOT_FOUND_OtherService(HttpStatus.NOT_FOUND, "기타 서비스를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
