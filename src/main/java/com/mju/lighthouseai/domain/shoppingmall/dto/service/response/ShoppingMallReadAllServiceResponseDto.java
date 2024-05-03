package com.mju.lighthouseai.domain.shoppingmall.dto.service.response;

public record ShoppingMallReadAllServiceResponseDto(
        String title,
        String location,
        int price,
        String opentime,
        String closetime,
        String constituency_name,
        String nickname,
        String region_name
){
}
