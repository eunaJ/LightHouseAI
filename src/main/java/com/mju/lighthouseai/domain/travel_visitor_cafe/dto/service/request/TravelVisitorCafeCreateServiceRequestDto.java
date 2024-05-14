package com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.request;

public record TravelVisitorCafeCreateServiceRequestDto(
        String menu,
        Integer price,
        String opentime,
        String closetime,
        String location,
        String cafe_title
) {
}
