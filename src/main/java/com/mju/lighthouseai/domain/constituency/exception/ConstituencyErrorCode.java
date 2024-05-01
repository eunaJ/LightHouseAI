package com.mju.lighthouseai.domain.constituency.exception;

import com.mju.lighthouseai.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ConstituencyErrorCode implements ErrorCode {

    // 403
    FORBIDDEN_ACCESS_CONSTITUENCY(HttpStatus.FORBIDDEN, "지역상세정보에 접근할 수 없습니다."),

    // 404
    NOT_FOUND_CONSTITUENCY(HttpStatus.NOT_FOUND, "지역상세정보를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
