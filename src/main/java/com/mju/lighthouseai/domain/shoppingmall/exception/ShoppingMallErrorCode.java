package com.mju.lighthouseai.domain.shoppingmall.exception;

import com.mju.lighthouseai.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ShoppingMallErrorCode implements ErrorCode {

    // 400
    ALREADY_EXIST_ShoppingMall(HttpStatus.BAD_REQUEST, "이미 쇼핑몰이 존재합니다."),

    // 403
    FORBIDDEN_ACCESS_ShoppingMall(HttpStatus.FORBIDDEN, "쇼핑몰에 접근할 수 없습니다."),

    // 404
    NOT_FOUND_ShoppingMall(HttpStatus.NOT_FOUND, "쇼핑몰을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
