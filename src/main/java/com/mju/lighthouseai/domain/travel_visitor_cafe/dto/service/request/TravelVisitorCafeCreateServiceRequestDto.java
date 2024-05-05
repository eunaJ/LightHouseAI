package com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.request;

public record TravelVisitorCafeCreateServiceRequestDto(
        int cost,
        String opentime,
        String closetime,
        String location,
        String cafe_title
) {
}
