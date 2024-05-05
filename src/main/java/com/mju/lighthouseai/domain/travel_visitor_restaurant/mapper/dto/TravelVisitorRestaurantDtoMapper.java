package com.mju.lighthouseai.domain.travel_visitor_restaurant.mapper.dto;

import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.controller.TravelVisitorRestaurantCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.request.TravelVisitorRestaurantCreateServiceRequestDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TravelVisitorRestaurantDtoMapper {
    TravelVisitorRestaurantCreateServiceRequestDto toTravelVisitorRestaurantCreateServiceDto(
            TravelVisitorRestaurantCreateControllerRequestDto controllerRequestDto
    );
}