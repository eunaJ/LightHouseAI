package com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.request;

public record TravelVisitorCafeUpdateServiceRequestDto(
        int cost,
        String opentime,
        String closetime,
        String location
) {
}