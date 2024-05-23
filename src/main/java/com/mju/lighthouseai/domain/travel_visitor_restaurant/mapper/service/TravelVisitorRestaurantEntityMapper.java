package com.mju.lighthouseai.domain.travel_visitor_restaurant.mapper.service;

import com.mju.lighthouseai.domain.restaurant.entity.Restaurant;
import com.mju.lighthouseai.domain.travel.entity.Travel;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.request.TravelVisitorRestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.response.TravelVisitorRestaurantReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.entity.TravelVisitorRestaurant;
import com.mju.lighthouseai.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TravelVisitorRestaurantEntityMapper {
    @Mapping(source = "requestDto.price", target = "price")
    @Mapping(source = "requestDto.menu", target = "menu")
    @Mapping(source = "requestDto.content",target = "content")
    @Mapping(source = "requestDto.opentime", target = "opentime")
    @Mapping(source = "requestDto.closetime", target = "closetime")
    @Mapping(source = "requestDto.location", target = "location")
    @Mapping(source = "restaurant", target = "restaurant")
    @Mapping(source = "image_url", target = "image_url")
    @Mapping(source = "travel",target = "travel")
    @Mapping(source = "user",target = "user")
    TravelVisitorRestaurant toTravelVisitorRestaurant(
        TravelVisitorRestaurantCreateServiceRequestDto requestDto,
        String image_url,
        User user,
        Restaurant restaurant,
        Travel travel);

    default String toUserName(User user){
        return user.getNickname();
    }
    default String toRestaurantTitle(Restaurant restaurant){
        return restaurant.getTitle();
    }
    @Mapping(source = "user", target = "nickname")
    @Mapping(source = "restaurant.title", target = "restaurant_title")
    TravelVisitorRestaurantReadAllServiceResponseDto toTravelVisitorRestaurantReadResponseDto(
            TravelVisitorRestaurant travelVisitorRestaurant);
    List<TravelVisitorRestaurantReadAllServiceResponseDto> toTravelVisitorRestaurantReadAllResponseDto(
            List<TravelVisitorRestaurant> travelVisitorRestaurants);
}