package com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.request;

public record TravelVisitorRestaurantCreateServiceRequestDto(
        String menu,
        int price,
        String opentime,
        String closetime,
        String location,
        String restaurant_title
) {
}
