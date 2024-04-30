package com.mju.lighthouseai.domain.tour_list.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.mju.lighthouseai.domain.cafe.dto.controller.CafeCreateControllerRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.controller.CafeUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.tour_list.dto.controller.TourListCreateControllerRequestDto;
import com.mju.lighthouseai.domain.tour_list.dto.controller.TourListUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.tour_list.dto.service.TourListCreateServiceRequestDto;
import com.mju.lighthouseai.domain.tour_list.dto.service.TourListUpdateServiceRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface TourListDtoMapper {
    TourListCreateServiceRequestDto toTourListCreateServiceDto(
        TourListCreateControllerRequestDto controllerRequestDto
    );
    TourListUpdateServiceRequestDto toTourListUpdateServiceDto(
        TourListUpdateControllerRequestDto controllerRequestDto
    );

}


