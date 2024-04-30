package com.mju.lighthouseai.domain.other_service.dto.service;

public record OtherServiceReadAllServiceResponseDto(
        String title,
        String location,
        int price,
        String opentime,
        String closetime,
        String constituency_name,
        String nickname,
        String region_name
) {
}
