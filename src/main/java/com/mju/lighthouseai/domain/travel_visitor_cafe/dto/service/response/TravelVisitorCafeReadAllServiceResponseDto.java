package com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.response;

public record TravelVisitorCafeReadAllServiceResponseDto (
        int cost,
        String opentime,
        String closetime,
        String location,
        String nickname,
        String cafe_title,
        String image_url
){
}
