package com.mju.lighthouseai.domain.cafe.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.mju.lighthouseai.domain.cafe.dto.controller.CafeCreateControllerRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface CafeEntityMapper {
    Cafe tocafe(CafeCreateServiceRequestDto requestDto);
}
