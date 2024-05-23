package com.mju.lighthouseai.domain.travel_visitor_tour_list.mapper.dto;

import com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.controller.TravelVisitorTourListCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.controller.TravelVisitorTourListUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.service.request.TravelVisitorTourListCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.service.request.TravelVisitorTourListUpdateServiceRequestDto;
import java.util.List;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TravelVisitorTourListDtoMapper {
    TravelVisitorTourListCreateServiceRequestDto toTravelVisitorTourListCreateServiceDto(
            TravelVisitorTourListCreateControllerRequestDto controllerRequestDto
    );
    TravelVisitorTourListUpdateServiceRequestDto toTravelVisitorTourListUpdateServiceDto(
            TravelVisitorTourListUpdateControllerRequestDto controllerRequestDto
    );
    List<TravelVisitorTourListCreateServiceRequestDto> toTravelVisitorTourListCreateServiceDtos(
      List<TravelVisitorTourListCreateControllerRequestDto> controllerRequestDtos
    );
}