package com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.service;

public record TravelVisitorShoppingMallCreateServiceRequestDto(
        int price,
        String opentime,
        String closetime,
        String location,
        String shoppingMall_title
) {
}
