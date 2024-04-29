package com.mju.lighthouseai.domain.other_service.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.mju.lighthouseai.domain.other_service.dto.controller.OtherServiceCreateControllerRequestDto;
import com.mju.lighthouseai.domain.other_service.dto.service.OtherServiceCreateServiceRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface OtherServiceDtoMapper {
    OtherServiceCreateServiceRequestDto toOtherServiceCreateServiceDto(
        OtherServiceCreateControllerRequestDto controllerRequestDto
    );

}
