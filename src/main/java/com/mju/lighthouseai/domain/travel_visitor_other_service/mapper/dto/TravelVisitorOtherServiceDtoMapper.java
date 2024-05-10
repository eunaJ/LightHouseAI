package com.mju.lighthouseai.domain.travel_visitor_other_service.mapper.dto;

import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.controller.TravelVisitorOtherServiceCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.controller.TravelVisitorOtherServiceUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.service.request.TravelVisitorOtherServiceCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.service.request.TravelVisitorOtherServiceUpdateServiceRequestDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TravelVisitorOtherServiceDtoMapper {
    TravelVisitorOtherServiceCreateServiceRequestDto toTravelVisitorOtherServiceCreateServiceDto(
            TravelVisitorOtherServiceCreateControllerRequestDto controllerRequestDto
    );

    TravelVisitorOtherServiceUpdateServiceRequestDto toTravelVisitorOtherServiceUpdateServiceDto(
            TravelVisitorOtherServiceUpdateControllerRequestDto controllerRequestDto
    );
}