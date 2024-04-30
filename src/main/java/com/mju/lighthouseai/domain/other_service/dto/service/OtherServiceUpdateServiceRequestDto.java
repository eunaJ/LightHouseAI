package com.mju.lighthouseai.domain.other_service.dto.service;

public record OtherServiceUpdateServiceRequestDto(
        String title,
        String location,
        int price,
        String opentime,
        String closetime,
        String constituency_name
) {
}
