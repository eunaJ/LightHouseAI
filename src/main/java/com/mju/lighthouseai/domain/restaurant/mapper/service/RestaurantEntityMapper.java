package com.mju.lighthouseai.domain.restaurant.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.mju.lighthouseai.domain.restaurant.dto.service.request.RestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.restaurant.entity.Restaurant;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface RestaurantEntityMapper {
    Restaurant torestaurant(RestaurantCreateServiceRequestDto requestDto);
}
