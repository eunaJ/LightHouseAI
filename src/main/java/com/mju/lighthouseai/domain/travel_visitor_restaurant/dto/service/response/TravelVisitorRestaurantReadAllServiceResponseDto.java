package com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.response;

public record TravelVisitorRestaurantReadAllServiceResponseDto(
        Long id,
        String menu,
        int price,
        String opentime,
        String closetime,
        String location,
        String nickname,
        String restaurant_title,
        String image_url,
        String content
) {
}
