package com.mju.lighthouseai.domain.other_service.dto.service;

public record OtherServiceCreateServiceRequestDto(
    String title,
    String location,
    Integer price,
    String constituency_name

) {

}
