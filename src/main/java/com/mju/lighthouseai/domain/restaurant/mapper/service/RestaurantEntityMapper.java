package com.mju.lighthouseai.domain.restaurant.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.region.entity.Region;
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
    Restaurant toRestaurant(RestaurantCreateServiceRequestDto requestDto, User user, Constituency constituency);
    default String toUserName(User user){
        return user.getNickname();
    }
    default String toConstituencyName(Constituency constituency){
        return constituency.getConstituency();
    }
    default String toConstituencyRegionName(Region region){
        return region.getRegion_name();
    }
    @Mapping(source = "user",target = "nickname")
    @Mapping(source = "constituency",target = "constituency_name")
    @Mapping(source = "constituency.region",target = "region_name")
    RestaurantReadAllServiceResponseDto toRestaurantReadResponseDto(Restaurant restaurant);

    List<RestaurantReadAllServiceResponseDto> toRestaurantReadAllResponseDto(List<Restaurant> restaurantsList);
}
