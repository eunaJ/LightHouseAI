package com.mju.lighthouseai.domain.restaurant.dto.controller;

public record RestaurantUpdateControllerRequestDto(
        String title,
        String location,
        Integer price,
        String menu,
        String opentime,
        String closetime,
        String constituency_name
) {
}
