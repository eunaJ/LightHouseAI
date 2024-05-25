package com.mju.lighthouseai.domain.restaurant.dto.controller;

public record RestaurantCreateControllerRequestDto(
    String title,
    String location,
    String opentime,
    String closetime,
    String constituency_name,
    Long item_id

) {

}
