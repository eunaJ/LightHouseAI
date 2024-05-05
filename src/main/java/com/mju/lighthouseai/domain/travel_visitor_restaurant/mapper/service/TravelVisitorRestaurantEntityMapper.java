package com.mju.lighthouseai.domain.travel_visitor_restaurant.mapper.service;

import com.mju.lighthouseai.domain.restaurant.entity.Restaurant;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.request.TravelVisitorRestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.response.TravelVisitorRestaurantReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.entity.TravelVisitorRestaurant;
import com.mju.lighthouseai.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TravelVisitorRestaurantEntityMapper {
    @Mapping(source = "requestDto.price", target = "price")
    @Mapping(source = "requestDto.menu", target = "menu")
    @Mapping(source = "requestDto.opentime", target = "opentime")
    @Mapping(source = "requestDto.closetime", target = "closetime")
    @Mapping(source = "requestDto.location", target = "location")
    @Mapping(source = "restaurant", target = "restaurant")
    TravelVisitorRestaurant toTravelVisitorRestaurant(TravelVisitorRestaurantCreateServiceRequestDto requestDto,
                                                      User user, Restaurant restaurant, String image_url);

    default String toUserName(User user){
        return user.getNickname();
    }
    default String toRestaurantTitle(Restaurant restaurant){
        return restaurant.getTitle();
    }
    @Mapping(source = "user", target = "nickname")
    @Mapping(source = "restaurant.title", target = "restaurant_title")
    TravelVisitorRestaurantReadAllServiceResponseDto toTravelVisitorRestaurantReadResponseDto(TravelVisitorRestaurant travelVisitorRestaurant);
}