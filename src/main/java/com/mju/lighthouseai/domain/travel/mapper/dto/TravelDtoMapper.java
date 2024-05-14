package com.mju.lighthouseai.domain.travel.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.mju.lighthouseai.domain.travel.dto.controller.TravelCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel.dto.controller.TravelUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.travel.dto.service.request.TravelCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel.dto.service.request.TravelUpdateServiceRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface TravelDtoMapper {
    TravelCreateServiceRequestDto toTravelCreateServiceDto(
            TravelCreateControllerRequestDto controllerRequestDto
    );

    TravelUpdateServiceRequestDto toTravelUpdateServiceDto(
            TravelUpdateControllerRequestDto controllerRequestDto
    );
}
