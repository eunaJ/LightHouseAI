package com.mju.lighthouseai.domain.travel_visitor_other_service.dto.controller;

public record TravelVisitorOtherServiceUpdateControllerRequestDto(
        int price,
        String opentime,
        String closetime,
        String location
) {
}
