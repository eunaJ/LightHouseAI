package com.mju.lighthouseai.domain.travel_visitor_cafe.dto.controller;

public record TravelVisitorCafeUpdateControllerRequestDto(
        String menu,
        Integer price,
        String content,
        String opentime,
        String closetime,
        String location,
        Boolean imageChange
) {
}