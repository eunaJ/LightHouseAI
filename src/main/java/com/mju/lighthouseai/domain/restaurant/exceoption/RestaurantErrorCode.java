package com.mju.lighthouseai.domain.restaurant.exceoption;

import com.mju.lighthouseai.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RestaurantErrorCode implements ErrorCode {

    // 400
    ALREADY_EXIST_Restaurant(HttpStatus.BAD_REQUEST, "이미 식당이 존재합니다."),

    // 403
    FORBIDDEN_ACCESS_Restaurant(HttpStatus.FORBIDDEN, "식당에 접근할 수 없습니다."),

    // 404
    NOT_FOUND_Restaurant(HttpStatus.NOT_FOUND, "식당을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
