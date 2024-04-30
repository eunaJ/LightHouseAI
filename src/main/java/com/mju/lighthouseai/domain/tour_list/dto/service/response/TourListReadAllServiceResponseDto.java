package com.mju.lighthouseai.domain.tour_list.dto.service.response;


public record TourListReadAllServiceResponseDto(
    String title,
    String location,
    int price,
    String opentime,
    String closetime,
    String constituency_name,
    String nickname,
    String region_name
) {
}
