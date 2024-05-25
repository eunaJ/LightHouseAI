package com.mju.lighthouseai.domain.other_service.dto.controller;

public record OtherServiceCreateControllerRequestDto(
    String title,
    String location,
    String opentime,
    String closetime,
    String constituency_name,
    Long item_id

) {

}
