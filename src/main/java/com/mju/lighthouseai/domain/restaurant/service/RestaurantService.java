package com.mju.lighthouseai.domain.restaurant.service;

import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.restaurant.dto.service.RestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.restaurant.dto.service.RestaurantUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.user.entity.User;

public interface RestaurantService {
    void createRestaurant(RestaurantCreateServiceRequestDto requestDto, User user);
    void updateRestaurant(Long id, RestaurantUpdateServiceRequestDto requestDto);
}
