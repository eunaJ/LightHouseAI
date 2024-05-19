package com.mju.lighthouseai.domain.travel_visitor_other_service.dto.service.response;

public record TravelVisitorOtherServiceReadAllServiceResponseDto(
        Long id,
        int price,
        String opentime,
        String closetime,
        String location,
        String nickname,
        String otherService_title,
        String image_url,
        String content
) {
}
