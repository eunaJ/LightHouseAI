package com.mju.lighthouseai.domain.travel.service;

import com.mju.lighthouseai.domain.travel.dto.service.request.TravelCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel.dto.service.request.TravelUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.travel.dto.service.response.TravelVisitorCafeReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.request.TravelVisitorCafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.request.TravelVisitorRestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.entity.TravelVisitorRestaurant;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.service.request.TravelVisitorShoppingMallCreateServiceRequestDto;
import com.mju.lighthouseai.domain.user.entity.User;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface TravelService {
    void createTravel(
        TravelCreateServiceRequestDto travelCreateServiceRequestDto,
         MultipartFile travelImage,
         List<TravelVisitorCafeCreateServiceRequestDto> travelVisitorCafeCreateServiceRequestDtos,
         List<MultipartFile> travelVisitorCafeImage,
        List<TravelVisitorRestaurantCreateServiceRequestDto> travelVisitorRestaurantCreateServiceRequestDtos,
        List<MultipartFile> travelVisitorRestaurantImage,
        final List<TravelVisitorShoppingMallCreateServiceRequestDto> travelVisitorShoppingMallCreateServiceRequestDtos,
        final List<MultipartFile> travelVisitorShoppingMallImages,
        User user
    ) throws IOException ;
    void updateTravel(Long id, TravelUpdateServiceRequestDto requestDto, User user,MultipartFile multipartFile) throws IOException;
    void deleteTravel(Long id, User user);
/*      TravelVisitorCafeReadAllServiceResponseDto readTravelVisitorCafe(Long id);
    List<TravelVisitorCafeReadAllServiceResponseDto> readAllTravelVisitorCafes();*/
}