package com.mju.lighthouseai.domain.travel_visitor_cafe.mapper.dto;

import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.controller.TravelVisitorCafeCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.controller.TravelVisitorCafeUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.request.TravelVisitorCafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.request.TravelVisitorCafeUpdateServiceRequestDto;
import java.util.List;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TravelVisitorCafeDtoMapper {
    TravelVisitorCafeCreateServiceRequestDto toTravelVisitorCafeCreateServiceDto(
            TravelVisitorCafeCreateControllerRequestDto controllerRequestDto
    );

    TravelVisitorCafeUpdateServiceRequestDto toTravelVisitorCafeUpdateServiceDto(
            TravelVisitorCafeUpdateControllerRequestDto controllerRequestDto
    );
    List<TravelVisitorCafeCreateServiceRequestDto> toTravelVisitorCafeCreateServiceDtos(
        List<TravelVisitorCafeCreateControllerRequestDto>controllerRequestDto );
}
