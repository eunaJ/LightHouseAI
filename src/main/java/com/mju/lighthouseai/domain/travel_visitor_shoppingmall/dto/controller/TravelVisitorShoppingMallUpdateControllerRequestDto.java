package com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.controller;

public record TravelVisitorShoppingMallUpdateControllerRequestDto(
        int price,
        String opentime,
        String closetime,
        String location
) {
}
