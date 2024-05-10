package com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.service.request;

public record TravelVisitorShoppingMallUpdateServiceRequestDto(
        int price,
        String opentime,
        String closetime,
        String location
) {
}
