package com.mju.lighthouseai.domain.travel_visitor_other_service.mapper.service;

import com.mju.lighthouseai.domain.other_service.entity.OtherServiceEntity;
import com.mju.lighthouseai.domain.travel.entity.Travel;
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
    @Mapping(source = "requestDto.content",target = "content")
    @Mapping(source = "requestDto.opentime", target = "opentime")
    @Mapping(source = "requestDto.closetime", target = "closetime")
    @Mapping(source = "requestDto.location", target = "location")
    @Mapping(source = "otherServiceEntity", target = "otherServiceEntity")
    @Mapping(source = "user",target = "user")
    @Mapping(source = "image_url",target = "image_url")
    @Mapping(source = "travel",target = "travel")
    TravelVisitorOtherServiceEntity toTravelVisitorOtherService(
            TravelVisitorOtherServiceCreateServiceRequestDto requestDto,
            String image_url,
            User user, OtherServiceEntity otherServiceEntity,
            Travel travel);

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

    List<TravelVisitorOtherServiceReadAllServiceResponseDto>
    toTravelVisitorOtherServiceReadAllResponseDto(
            List<TravelVisitorOtherServiceEntity> travelVisitorOtherServiceEntities);
}