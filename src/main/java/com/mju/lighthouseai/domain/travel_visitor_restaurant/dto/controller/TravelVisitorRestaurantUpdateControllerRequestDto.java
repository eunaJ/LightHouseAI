package com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.controller;

public record TravelVisitorRestaurantUpdateControllerRequestDto(
        String menu,
        int price,
        String opentime,
        String closetime,
        String location,
        String restaurant_title
) {
}
