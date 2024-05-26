package com.mju.lighthouseai.domain.cafe.dto.controller;

public record CafeCreateControllerRequestDto(
    String title,
    String location,
    String opentime,
    String closetime,
    String constituency_name,
    Long item_id

) {

}
