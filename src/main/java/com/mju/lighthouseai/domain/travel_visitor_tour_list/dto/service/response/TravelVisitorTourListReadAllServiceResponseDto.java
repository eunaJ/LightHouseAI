package com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.service.response;

public record TravelVisitorTourListReadAllServiceResponseDto(
        Long id,
        int price,
        String opentime,
        String closetime,
        String location,
        String nickname,
        String tourList_title,
        String image_url,
        String content
) {
}
