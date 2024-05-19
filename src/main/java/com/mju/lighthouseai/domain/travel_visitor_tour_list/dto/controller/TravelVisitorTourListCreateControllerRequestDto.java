package com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.controller;

public record TravelVisitorTourListCreateControllerRequestDto(
        Integer price,
        String content,
        String opentime,
        String closetime,
        String location,
        String tourList_title
) {
}
