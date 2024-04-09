package com.mju.lighthouseai.domain.cafe.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.mju.lighthouseai.domain.cafe.dto.controller.CafeCreateControllerRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeCreateServiceRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface CafeDtoMapper {
    CafeCreateServiceRequestDto toCafeCreateServiceDto(
        CafeCreateControllerRequestDto controllerRequestDto
    );

}
