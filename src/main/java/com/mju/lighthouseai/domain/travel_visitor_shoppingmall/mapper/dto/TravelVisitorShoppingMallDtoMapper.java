package com.mju.lighthouseai.domain.travel_visitor_shoppingmall.mapper.dto;

import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.controller.TravelVisitorShoppingMallCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.service.TravelVisitorShoppingMallCreateServiceRequestDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TravelVisitorShoppingMallDtoMapper {
    TravelVisitorShoppingMallCreateServiceRequestDto toTravelVisitorShoppingMallCreateServiceDto(
            TravelVisitorShoppingMallCreateControllerRequestDto controllerRequestDto
    );
}