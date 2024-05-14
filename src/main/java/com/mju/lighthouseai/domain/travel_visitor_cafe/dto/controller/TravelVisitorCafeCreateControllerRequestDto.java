package com.mju.lighthouseai.domain.travel_visitor_cafe.dto.controller;

public record TravelVisitorCafeCreateControllerRequestDto(
        String menu,
        Integer price,
        String opentime,
        String closetime,
        String location,
        String cafe_title
) {
}
