package com.mju.lighthouseai.domain.travel_visitor_restaurant.service;

import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.request.TravelVisitorRestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.request.TravelVisitorRestaurantUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TravelVisitorRestaurantService {
    void createTravelVisitorRestaurant(TravelVisitorRestaurantCreateServiceRequestDto requestDto, User user,
                                       MultipartFile multipartFile) throws IOException;
    void updateTravelVisitorRestaurant(Long id, TravelVisitorRestaurantUpdateServiceRequestDto requestDto, User user);
}