package com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.controller;

public record TravelVisitorTourListUpdateControllerRequestDto(
        Integer price,
        String content,
        String opentime,
        String closetime,
        String location,
        Boolean imageChange
) {
}
