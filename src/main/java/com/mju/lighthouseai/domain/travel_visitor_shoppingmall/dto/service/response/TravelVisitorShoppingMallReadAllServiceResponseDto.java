package com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.service.response;

public record TravelVisitorShoppingMallReadAllServiceResponseDto(
        Long id,
        int price,
        String opentime,
        String closetime,
        String location,
        String nickname,
        String shoppingMall_title,
        String image_url,
        String content
) {
}
