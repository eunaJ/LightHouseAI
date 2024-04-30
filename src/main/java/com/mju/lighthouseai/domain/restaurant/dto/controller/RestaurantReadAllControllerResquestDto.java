package com.mju.lighthouseai.domain.restaurant.dto.controller;

public record RestaurantReadAllControllerResquestDto (
        String title,
        String location,
        int price,
        String menu,
        String opentime,
        String closetime,
        String user_name
) {
}
