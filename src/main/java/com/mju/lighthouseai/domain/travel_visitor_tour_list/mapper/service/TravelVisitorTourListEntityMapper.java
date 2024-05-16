package com.mju.lighthouseai.domain.travel_visitor_tour_list.mapper.service;

import com.mju.lighthouseai.domain.tour_list.entity.TourList;
import com.mju.lighthouseai.domain.travel.entity.Travel;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.service.request.TravelVisitorTourListCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.service.response.TravelVisitorTourListReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.entity.TravelVisitorTourList;
import com.mju.lighthouseai.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TravelVisitorTourListEntityMapper {
    @Mapping(source = "requestDto.price", target = "price")
    @Mapping(source = "requestDto.opentime", target = "opentime")
    @Mapping(source = "requestDto.closetime", target = "closetime")
    @Mapping(source = "requestDto.location", target = "location")
    @Mapping(source = "image_url", target = "image_url")
    @Mapping(source = "tourList", target = "tourList")
    @Mapping(source = "travel",target = "travel")
    TravelVisitorTourList toTravelVisitorTourList(TravelVisitorTourListCreateServiceRequestDto requestDto,
        String image_url, User user, TourList tourList, Travel travel);

    default String toUserName(User user){
        return user.getNickname();
    }
    default String toTourListTitle(TourList tourList){
        return tourList.getTitle();
    }
    @Mapping(source = "user", target = "nickname")
    @Mapping(source = "tourList.title", target = "tourList_title")
    TravelVisitorTourListReadAllServiceResponseDto toTravelVisitorTourListReadResponseDto(
            TravelVisitorTourList travelVisitorTourList);

    List<TravelVisitorTourListReadAllServiceResponseDto> toTravelVisitorTourListReadAllResponseDto(
            List<TravelVisitorTourList> travelVisitorTourLists);
}
