package com.mju.lighthouseai.domain.restaurant.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.restaurant.dto.service.request.RestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.restaurant.dto.service.response.RestaurantReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.restaurant.entity.Restaurant;
import com.mju.lighthouseai.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = SPRING)
public interface RestaurantEntityMapper {
    @Mapping(source = "constituency",target = "constituency")
    Restaurant torestaurant(RestaurantCreateServiceRequestDto requestDto, User user, Constituency constituency);
    default String toUserName(User user){
        return user.getNickname();
    }
    @Mapping(source = "user",target = "user_name")
    RestaurantReadAllServiceResponseDto toRestaurantReadResponseDto(Restaurant restaurant);

    List<RestaurantReadAllServiceResponseDto> toRestaurantReadAllResponseDto(List<Restaurant> Rrestaurants);
}
