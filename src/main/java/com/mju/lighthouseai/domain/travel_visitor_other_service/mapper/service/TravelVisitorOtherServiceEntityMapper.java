package com.mju.lighthouseai.domain.travel_visitor_other_service.mapper.service;

import com.mju.lighthouseai.domain.other_service.entity.OtherServiceEntity;
import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.service.request.TravelVisitorOtherServiceCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.service.response.TravelVisitorOtherServiceReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.travel_visitor_other_service.entity.TravelVisitorOtherServiceEntity;
import com.mju.lighthouseai.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TravelVisitorOtherServiceEntityMapper {
    @Mapping(source = "requestDto.price", target = "price")
    @Mapping(source = "requestDto.opentime", target = "opentime")
    @Mapping(source = "requestDto.closetime", target = "closetime")
    @Mapping(source = "requestDto.location", target = "location")
    @Mapping(source = "otherServiceEntity", target = "otherServiceEntity")
    TravelVisitorOtherServiceEntity toTravelVisitorOtherService(
            TravelVisitorOtherServiceCreateServiceRequestDto requestDto,
            User user, OtherServiceEntity otherServiceEntity, String image_url);

    default String toUserName(User user){
        return user.getNickname();
    }
    default String toOtherServiceTitle(OtherServiceEntity otherServiceEntity){
        return otherServiceEntity.getTitle();
    }
    @Mapping(source = "user", target = "nickname")
    @Mapping(source = "otherServiceEntity.title", target = "otherService_title")
    TravelVisitorOtherServiceReadAllServiceResponseDto toTravelVisitorOtherServiceReadResponseDto(
            TravelVisitorOtherServiceEntity travelVisitorOtherServiceEntity);
}