package com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.controller;

public record TravelVisitorRestaurantUpdateControllerRequestDto(
        String menu,
        Integer price,
        String opentime,
        String closetime,
        String location,
        Boolean imageChange
) {
}
