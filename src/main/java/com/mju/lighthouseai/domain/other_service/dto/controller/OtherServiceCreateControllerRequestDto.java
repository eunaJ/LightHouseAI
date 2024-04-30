package com.mju.lighthouseai.domain.other_service.dto.controller;

public record OtherServiceCreateControllerRequestDto(
    String title,
    String location,
    Integer price,
    String constituency_name

) {

}
