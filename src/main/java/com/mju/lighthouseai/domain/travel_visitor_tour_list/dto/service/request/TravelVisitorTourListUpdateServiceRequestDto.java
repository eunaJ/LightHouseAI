package com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.service.request;

public record TravelVisitorTourListUpdateServiceRequestDto(
        Integer price,
        String content,
        String opentime,
        String closetime,
        String location,
        Boolean imageChange
) {
}