package com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.controller;

public record TravelVisitorShoppingMallCreateControllerRequestDto(
        Integer price,
        String content,
        String opentime,
        String closetime,
        String location,
        String shoppingMall_title
) {
}
