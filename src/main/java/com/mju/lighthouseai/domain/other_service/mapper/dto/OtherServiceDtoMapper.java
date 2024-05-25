package com.mju.lighthouseai.domain.other_service.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.mju.lighthouseai.domain.other_service.dto.controller.OtherServiceCreateControllerRequestDto;
import com.mju.lighthouseai.domain.other_service.dto.controller.OtherServiceUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.other_service.dto.service.request.OtherServiceCreateServiceRequestDto;
import com.mju.lighthouseai.domain.other_service.dto.service.request.OtherServiceUpdateServiceRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface OtherServiceDtoMapper {
    OtherServiceCreateServiceRequestDto toOtherServiceCreateServiceDto(
        OtherServiceCreateControllerRequestDto controllerRequestDto
    );

    OtherServiceUpdateServiceRequestDto toOtherServiceUpdateServiceDto(
            OtherServiceUpdateControllerRequestDto controllerRequestDto
    );
}
