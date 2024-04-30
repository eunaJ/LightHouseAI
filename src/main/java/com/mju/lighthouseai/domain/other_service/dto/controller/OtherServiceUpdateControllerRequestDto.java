package com.mju.lighthouseai.domain.other_service.dto.controller;

public record OtherServiceUpdateControllerRequestDto(
        String title,
        String location,
        int price,
        String opentime,
        String closetime,
        String constituency_name
) {
}
