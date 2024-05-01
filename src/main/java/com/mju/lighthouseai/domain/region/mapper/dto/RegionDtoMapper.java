package com.mju.lighthouseai.domain.region.mapper.dto;

import com.mju.lighthouseai.domain.region.dto.controller.RegionCreateControllerRequestDto;
import com.mju.lighthouseai.domain.region.dto.controller.RegionUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.region.dto.service.request.RegionCreateServiceRequestDto;
import com.mju.lighthouseai.domain.region.dto.service.request.RegionUpdateServiceRequestDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RegionDtoMapper {
    RegionCreateServiceRequestDto toRegionCreateServiceDto(
            RegionCreateControllerRequestDto controllerRequestDto
    );

    RegionUpdateServiceRequestDto toRegionUpdateServiceDto(
            RegionUpdateControllerRequestDto controllerRequestDto
    );
}
