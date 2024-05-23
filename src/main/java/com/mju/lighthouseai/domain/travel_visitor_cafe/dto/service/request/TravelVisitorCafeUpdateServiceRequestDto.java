package com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.request;

public record TravelVisitorCafeUpdateServiceRequestDto(
        String menu,
        Integer price,
        String content,
        String opentime,
        String closetime,
        String location,
        Boolean imageChange
) {
}