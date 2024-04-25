package com.mju.lighthouseai.domain.region.exception;

import com.mju.lighthouseai.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RegionErrorCode implements ErrorCode {

    // 400
    ALREADY_EXIST_REGION(HttpStatus.BAD_REQUEST, "이미 지역이 존재합니다."),

    // 403
    FORBIDDEN_ACCESS_CAFE(HttpStatus.FORBIDDEN, "지역에 접근할 수 없습니다."),

    // 404
    NOT_FOUND_REGION(HttpStatus.NOT_FOUND, "지역을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
