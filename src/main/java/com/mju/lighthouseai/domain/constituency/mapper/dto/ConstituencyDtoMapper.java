package com.mju.lighthouseai.domain.constituency.mapper.dto;

import com.mju.lighthouseai.domain.constituency.dto.controller.ConstituencyCreateControllerRequestDto;
import com.mju.lighthouseai.domain.constituency.dto.service.request.ConstituencyCreateServiceRequestDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ConstituencyDtoMapper {
    ConstituencyCreateServiceRequestDto toConstituencyCreateServiceDto(
            ConstituencyCreateControllerRequestDto controllerRequestDto
    );
}