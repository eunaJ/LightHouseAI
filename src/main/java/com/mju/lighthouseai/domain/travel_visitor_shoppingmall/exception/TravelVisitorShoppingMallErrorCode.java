package com.mju.lighthouseai.domain.travel_visitor_shoppingmall.exception;

import com.mju.lighthouseai.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TravelVisitorShoppingMallErrorCode implements ErrorCode {
    // 400
    ALREADY_EXIST_TRAVEL_VISITOR_ShoppingMall(HttpStatus.BAD_REQUEST,
            "이미 여행지 방문지 쇼핑몰이 존재합니다."),

    // 403
    FORBIDDEN_ACCESS_TRAVEL_VISITOR_ShoppingMall(HttpStatus.FORBIDDEN,
            "여행지 방문지 쇼피몰에 접근할 수 없습니다."),

    // 404
    NOT_FOUND_TRAVEL_VISITOR_ShoppingMall(HttpStatus.NOT_FOUND,
            "여행지 방문지 쇼핑몰을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}