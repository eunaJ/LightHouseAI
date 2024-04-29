package com.mju.lighthouseai.domain.tour_list.exceoption;

import com.mju.lighthouseai.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TourListErrorCode implements ErrorCode {

    // 400
    ALREADY_EXIST_CAFE(HttpStatus.BAD_REQUEST, "이미 관광지가 존재합니다."),

    // 403
    FORBIDDEN_ACCESS_CAFE(HttpStatus.FORBIDDEN, "관광지에 접근할 수 없습니다."),

    // 404
    NOT_FOUND_CAFE(HttpStatus.NOT_FOUND, "관광지를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
