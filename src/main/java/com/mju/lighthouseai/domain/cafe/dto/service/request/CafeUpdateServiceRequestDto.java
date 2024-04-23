package com.mju.lighthouseai.domain.cafe.dto.service.request;

public record CafeUpdateServiceRequestDto(
    String title,
    String location,
    Integer price,
    String menu,
    String opentime,
    String closetime
) {

}
