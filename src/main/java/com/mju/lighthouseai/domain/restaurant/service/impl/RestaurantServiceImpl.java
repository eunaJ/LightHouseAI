package com.mju.lighthouseai.domain.restaurant.service.impl;

import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.constituency.exception.ConstituencyErrorCode;
import com.mju.lighthouseai.domain.constituency.exception.NotFoundConstituencyException;
import com.mju.lighthouseai.domain.constituency.repository.ConstituencyRepository;
import com.mju.lighthouseai.domain.restaurant.dto.service.RestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.restaurant.entity.Restaurant;
import com.mju.lighthouseai.domain.restaurant.mapper.service.RestaurantEntityMapper;
import com.mju.lighthouseai.domain.restaurant.repository.RestaurantRepository;
import com.mju.lighthouseai.domain.restaurant.service.RestaurantService;
import com.mju.lighthouseai.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;
    private final ConstituencyRepository constituencyRepository;

    public void createRestaurant(RestaurantCreateServiceRequestDto requestDto, User user){
        Constituency constituency = constituencyRepository.findByConstituency(requestDto.constituency_name())
            .orElseThrow(()->new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
        Restaurant restaurant = restaurantEntityMapper.torestaurant(requestDto,user,constituency);
        restaurantRepository.save(restaurant);
    }

}
