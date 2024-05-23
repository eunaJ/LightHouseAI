package com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.service.request;

public record TravelVisitorShoppingMallUpdateServiceRequestDto(
        Integer price,
        String content,
        String opentime,
        String closetime,
        String location,
        Boolean imageChange
) {
}
