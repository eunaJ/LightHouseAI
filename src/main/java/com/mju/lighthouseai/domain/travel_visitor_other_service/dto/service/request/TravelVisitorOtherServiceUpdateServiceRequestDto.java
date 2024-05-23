package com.mju.lighthouseai.domain.travel_visitor_other_service.dto.service.request;

public record TravelVisitorOtherServiceUpdateServiceRequestDto(
        Integer price,
        String content,
        String opentime,
        String closetime,
        String location,
        Boolean imageChange
) {
}
