package com.mju.lighthouseai.domain.travel_visitor_cafe.dto.controller;

public record TravelVisitorCafeUpdateControllerRequestDto(
        String menu,
        int cost,
        String opentime,
        String closetime,
        String location
) {
}