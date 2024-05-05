package com.mju.lighthouseai.domain.travel_visitor_cafe.dto.controller;

public record TravelVisitorCafeUpdateControllerRequestDto(
        int cost,
        String opentime,
        String closetime,
        String location
) {
}