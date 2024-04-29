package com.mju.lighthouseai.domain.other_service.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.other_service.dto.service.OtherServiceCreateServiceRequestDto;
import com.mju.lighthouseai.domain.other_service.entity.OtherServiceEntity;
import com.mju.lighthouseai.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface OtherServiceEntityMapper {
    @Mapping(source = "constituency",target = "constituency")
    OtherServiceEntity toOtherService(OtherServiceCreateServiceRequestDto requestDto, User user, Constituency constituency);
}
