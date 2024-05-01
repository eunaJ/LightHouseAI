package com.mju.lighthouseai.domain.constituency.mapper.service;

import com.mju.lighthouseai.domain.constituency.dto.service.request.ConstituencyCreateServiceRequestDto;
import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.user.entity.User;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ConstituencyEntityMapper {
    Constituency toConstituency(ConstituencyCreateServiceRequestDto requestDto, User user);
}