package com.mju.lighthouseai.domain.region.mapper.service;

import com.mju.lighthouseai.domain.region.dto.service.request.RegionCreateServiceRequestDto;
import com.mju.lighthouseai.domain.region.entity.Region;
import com.mju.lighthouseai.domain.user.entity.User;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RegionEntityMapper {
    Region toRegion(RegionCreateServiceRequestDto requestDto, User user);
}
