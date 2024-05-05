package com.mju.lighthouseai.domain.travel_visitor_cafe.dto.controller;

public record TravelVisitorCafeUpdateControllerRequestDto(
        String menu,
        int price,
        String opentime,
        String closetime,
        String location
) {
}