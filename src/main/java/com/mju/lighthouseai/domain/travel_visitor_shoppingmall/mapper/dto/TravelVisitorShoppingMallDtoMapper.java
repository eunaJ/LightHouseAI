package com.mju.lighthouseai.domain.travel_visitor_shoppingmall.mapper.dto;

import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.controller.TravelVisitorCafeCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.request.TravelVisitorCafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.controller.TravelVisitorShoppingMallCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.controller.TravelVisitorShoppingMallUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.service.request.TravelVisitorShoppingMallCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.service.request.TravelVisitorShoppingMallUpdateServiceRequestDto;
import java.util.List;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TravelVisitorShoppingMallDtoMapper {
    TravelVisitorShoppingMallCreateServiceRequestDto toTravelVisitorShoppingMallCreateServiceDto(
            TravelVisitorShoppingMallCreateControllerRequestDto controllerRequestDto
    );
    TravelVisitorShoppingMallUpdateServiceRequestDto toTravelVisitorShoppingMallUpdateServiceDto(
            TravelVisitorShoppingMallUpdateControllerRequestDto controllerRequestDto
    );
    List<TravelVisitorShoppingMallCreateServiceRequestDto> toTravelVisitorShoppingMallCreateServiceDtos(
        List<TravelVisitorShoppingMallCreateControllerRequestDto>controllerRequestDto );

}