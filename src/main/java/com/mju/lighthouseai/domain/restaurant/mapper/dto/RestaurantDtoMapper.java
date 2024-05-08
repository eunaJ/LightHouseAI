package com.mju.lighthouseai.domain.restaurant.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.mju.lighthouseai.domain.restaurant.dto.controller.RestaurantUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.restaurant.dto.service.request.RestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.restaurant.dto.controller.RestaurantCreateControllerRequestDto;
import com.mju.lighthouseai.domain.restaurant.dto.service.request.RestaurantUpdateServiceRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface RestaurantDtoMapper {
    RestaurantCreateServiceRequestDto toRestaurantCreateServiceDto(
        RestaurantCreateControllerRequestDto controllerRequestDto
    );

    RestaurantUpdateServiceRequestDto toRestaurantUpdateServiceDto(
            RestaurantUpdateControllerRequestDto controllerRequestDto
    );
}
