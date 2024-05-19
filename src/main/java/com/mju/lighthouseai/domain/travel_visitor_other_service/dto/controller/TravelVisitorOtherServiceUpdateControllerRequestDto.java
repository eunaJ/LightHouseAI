package com.mju.lighthouseai.domain.travel_visitor_other_service.dto.controller;

public record TravelVisitorOtherServiceUpdateControllerRequestDto(
        Integer price,
        String content,
        String opentime,
        String closetime,
        String location,
        Boolean imageChange
) {
}
