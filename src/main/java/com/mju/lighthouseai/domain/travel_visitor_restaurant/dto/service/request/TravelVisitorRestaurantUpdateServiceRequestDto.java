package com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.request;

public record TravelVisitorRestaurantUpdateServiceRequestDto(
    String menu,
    Integer price,
    String content,
    String opentime,
    String closetime,
    String location,
    Boolean imageChange
) {
}
