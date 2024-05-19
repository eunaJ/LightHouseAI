package com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.controller;

public record TravelVisitorShoppingMallUpdateControllerRequestDto(
        Integer price,
        String content,
        String opentime,
        String closetime,
        String location,
        Boolean imageChange

) {
}
