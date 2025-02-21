package com.mju.lighthouseai.domain.tour_list.dto.service.request;

public record TourListUpdateServiceRequestDto(
    String title,
    String location,
    int price,
    String opentime,
    String closetime,
    String constituency_name
) {

}
