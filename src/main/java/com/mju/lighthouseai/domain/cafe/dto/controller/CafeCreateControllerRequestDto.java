package com.mju.lighthouseai.domain.cafe.dto.controller;

public record CafeCreateControllerRequestDto(
    String title,
    String location,
    Integer price,
    String menu,
    String opentime,
    String closetime
) {

}
