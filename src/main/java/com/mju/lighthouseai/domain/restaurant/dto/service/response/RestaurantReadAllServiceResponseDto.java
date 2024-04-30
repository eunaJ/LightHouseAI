package com.mju.lighthouseai.domain.restaurant.dto.service.response;

public record RestaurantReadAllServiceResponseDto (
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
