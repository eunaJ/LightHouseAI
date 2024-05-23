package com.mju.lighthouseai.domain.travel_visitor_other_service.dto.service.request;

public record TravelVisitorOtherServiceCreateServiceRequestDto(
        Integer price,
        String content,
        String opentime,
        String closetime,
        String location,
        String otherService_title
) {
}
