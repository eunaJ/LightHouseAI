package com.mju.lighthouseai.domain.travel_visitor_cafe.mapper.service;

import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.TravelVisitorCafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.entity.TravelVisitorCafe;
import com.mju.lighthouseai.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TravelVisitorCafeEntityMapper {
    @Mapping(source = "requestDto.opentime", target = "opentime")
    @Mapping(source = "requestDto.closetime", target = "closetime")
    @Mapping(source = "requestDto.location", target = "location")
    @Mapping(source = "cafe", target = "cafe")
    TravelVisitorCafe toTravelVisitorCafe(TravelVisitorCafeCreateServiceRequestDto requestDto,
                                          User user, Cafe cafe, String image_url);
}
