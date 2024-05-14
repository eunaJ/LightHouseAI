package com.mju.lighthouseai.domain.travel.exception;

import com.mju.lighthouseai.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TravelErrorCode implements ErrorCode {
    // 400
    ALREADY_EXIST_TRAVEL_VISITOR_CAFE(HttpStatus.BAD_REQUEST, "이미 여행지가 존재합니다."),

    // 403
    FORBIDDEN_ACCESS_TRAVEL_VISITOR_CAFE(HttpStatus.FORBIDDEN, "여행지에 접근할 수 없습니다."),

    // 404
    NOT_FOUND_TRAVEL(HttpStatus.NOT_FOUND, "여행지를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}