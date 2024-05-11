package com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.controller;

public record TravelVisitorTourListUpdateControllerRequestDto(
        int price,
        String opentime,
        String closetime,
        String location
) {
}
