package com.mju.lighthouseai.domain.cafe.dto.controller;

public record CafeUpdateControllerRequestDto(
    String title,
    String location,
    Integer price,
    String menu,
    String opentime,
    String closetime
) {

}
