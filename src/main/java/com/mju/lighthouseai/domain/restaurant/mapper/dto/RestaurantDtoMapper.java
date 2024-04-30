package com.mju.lighthouseai.domain.restaurant.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.mju.lighthouseai.domain.restaurant.dto.service.RestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.restaurant.dto.controller.RestaurantCreateControllerRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface RestaurantDtoMapper {
    RestaurantCreateServiceRequestDto toRestaurantCreateServiceDto(
        RestaurantCreateControllerRequestDto controllerRequestDto
    );

}
