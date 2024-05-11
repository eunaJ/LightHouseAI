package com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.service.request;

public record TravelVisitorTourListUpdateServiceRequestDto(
        int price,
        String opentime,
        String closetime,
        String location
) {
}