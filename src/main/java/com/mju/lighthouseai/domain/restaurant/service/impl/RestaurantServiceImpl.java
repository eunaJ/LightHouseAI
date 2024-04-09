package com.mju.lighthouseai.domain.restaurant.service.impl;

import com.mju.lighthouseai.domain.restaurant.dto.service.request.RestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.restaurant.entity.Restaurant;
import com.mju.lighthouseai.domain.restaurant.mapper.service.RestaurantEntityMapper;
import com.mju.lighthouseai.domain.restaurant.repository.RestaurantRepository;
import com.mju.lighthouseai.domain.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;

    public void createRestaurant(RestaurantCreateServiceRequestDto requestDto){
        Restaurant restaurant = restaurantEntityMapper.torestaurant(requestDto);
        restaurantRepository.save(restaurant);
    }

}
