package com.mju.lighthouseai.domain.other_service.dto.service.response;

public record OtherServiceReadAllServiceResponseDto(
        String title,
        String location,
        String opentime,
        String closetime,
        String constituency_name,
        String nickname,
        String region_name
) {
}
