package com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.request;

public record TravelVisitorCafeUpdateServiceRequestDto(
        String menu,
        int cost,
        String opentime,
        String closetime,
        String location
) {
}