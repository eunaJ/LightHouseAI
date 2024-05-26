package com.mju.lighthouseai.domain.restaurant.dto.service.request;

public record RestaurantCreateServiceRequestDto(
    String title,
    String location,
    String opentime,
    String closetime,
    String constituency_name,
    Long item_id

) {

}
