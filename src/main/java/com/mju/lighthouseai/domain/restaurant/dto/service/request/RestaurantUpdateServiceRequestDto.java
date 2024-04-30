package com.mju.lighthouseai.domain.restaurant.dto.service.request;

public record RestaurantUpdateServiceRequestDto(
        String title,
        String location,
        Integer price,
        String menu,
        String opentime,
        String closetime,
        String constituency_name
) {
}