package com.mju.lighthouseai.domain.tour_list.dto.controller;

public record TourListUpdateControllerRequestDto(
    String title,
    String location,
    int price,
    String opentime,
    String closetime,
    String constituency_name
) {

}
