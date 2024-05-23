package com.mju.lighthouseai.domain.travel_visitor_restaurant.service;

import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.service.response.TravelVisitorOtherServiceReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.request.TravelVisitorRestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.request.TravelVisitorRestaurantUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.response.TravelVisitorRestaurantReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TravelVisitorRestaurantService {
    void createTravelVisitorRestaurant(Long id,TravelVisitorRestaurantCreateServiceRequestDto requestDto, User user,
                                       MultipartFile multipartFile) throws IOException;
    void updateTravelVisitorRestaurant(
        Long id,
        TravelVisitorRestaurantUpdateServiceRequestDto requestDto,
        MultipartFile multipartFile ,
        User user) throws IOException;
    void deleteTravelVisitorRestaurant(Long id, User user);
    TravelVisitorRestaurantReadAllServiceResponseDto readTravelVisitorRestaurant(Long id);
    List<TravelVisitorRestaurantReadAllServiceResponseDto> readAllTravelVisitorRestaurants();
    List<TravelVisitorRestaurantReadAllServiceResponseDto> readAllTravelVisitorRestaurantsByTravelId(Long id);

}