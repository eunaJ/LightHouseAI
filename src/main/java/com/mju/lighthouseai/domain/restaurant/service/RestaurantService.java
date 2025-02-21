package com.mju.lighthouseai.domain.restaurant.service;

import com.mju.lighthouseai.domain.restaurant.dto.service.request.RestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.restaurant.dto.service.request.RestaurantUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.restaurant.dto.service.response.RestaurantReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.user.entity.User;

import java.util.List;

public interface RestaurantService {

    void createRestaurant(RestaurantCreateServiceRequestDto requestDto, User user);

    void updateRestaurant(Long id, RestaurantUpdateServiceRequestDto requestDto, User user);

    void deleteRestaurant(Long id, User user);

    List<RestaurantReadAllServiceResponseDto> readAllRestaurants();

    RestaurantReadAllServiceResponseDto readRestaurant(Long id);

    List<RestaurantReadAllServiceResponseDto> readConstituencyRestaurants(Long id);
}
