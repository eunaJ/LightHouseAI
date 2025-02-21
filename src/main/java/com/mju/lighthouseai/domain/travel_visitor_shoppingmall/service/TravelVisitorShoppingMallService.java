package com.mju.lighthouseai.domain.travel_visitor_shoppingmall.service;

import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.response.TravelVisitorRestaurantReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.service.request.TravelVisitorShoppingMallCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.service.request.TravelVisitorShoppingMallUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.service.response.TravelVisitorShoppingMallReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TravelVisitorShoppingMallService {
    void createTravelVisitorShoppingMall(Long id,TravelVisitorShoppingMallCreateServiceRequestDto requestDto,
                                         User user, MultipartFile multipartFile) throws IOException;
    void updateTravelVisitorShoppingMall(Long id,
                                         TravelVisitorShoppingMallUpdateServiceRequestDto requestDto,MultipartFile multipartFile ,User user) throws IOException;
    void deleteTravelVisitorShoppingMall(Long id, User user);
    TravelVisitorShoppingMallReadAllServiceResponseDto readTravelVisitorShoppingMall(Long id);
    List<TravelVisitorShoppingMallReadAllServiceResponseDto> readAllTravelVisitorShoppingMalls();
    List<TravelVisitorShoppingMallReadAllServiceResponseDto> readAllTravelVisitorShoppingMallsByTravelId(Long id);
}