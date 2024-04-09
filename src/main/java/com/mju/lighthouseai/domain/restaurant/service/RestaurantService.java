package com.mju.lighthouseai.domain.restaurant.service;

import com.mju.lighthouseai.domain.restaurant.dto.service.request.RestaurantCreateServiceRequestDto;

public interface RestaurantService {
    void createRestaurant(RestaurantCreateServiceRequestDto requestDto);

}
