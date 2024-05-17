package com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.service.request;

public record TravelVisitorTourListCreateServiceRequestDto(
        Integer price,
        String opentime,
        String closetime,
        String location,
        String tourList_title
) {
}
