package com.mju.lighthouseai.domain.restaurant.dto.controller;

public record RestaurantReadAllControllerResquestDto (
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
