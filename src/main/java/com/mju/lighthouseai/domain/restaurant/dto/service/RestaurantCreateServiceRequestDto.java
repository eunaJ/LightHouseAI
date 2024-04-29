package com.mju.lighthouseai.domain.restaurant.dto.service;

public record RestaurantCreateServiceRequestDto(
    String title,
    String location,
    Integer price,
    String menu,
    String opentime,
    String closetime,
    String constituency_name

) {

}
