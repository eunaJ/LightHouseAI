package com.mju.lighthouseai.domain.travel_visitor_restaurant.exception;

import com.mju.lighthouseai.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TravelVisitorRestaurantErrorCode implements ErrorCode {
    // 400
    ALREADY_EXIST_TRAVEL_VISITOR_RESTAURANT(HttpStatus.BAD_REQUEST, "이미 여행지 방문지 음식점이 존재합니다."),

    // 403
    FORBIDDEN_ACCESS_TRAVEL_VISITOR_RESTAURANT(HttpStatus.FORBIDDEN, "여행지 방문지 음식점에 접근할 수 없습니다."),

    // 404
    NOT_FOUND_TRAVEL_VISITOR_RESTAURANT(HttpStatus.NOT_FOUND, "여행지 방문지 음식점을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}