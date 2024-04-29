package com.mju.lighthouseai.domain.tour_list.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.response.CafeReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.tour_list.dto.service.TourListCreateServiceRequestDto;
import com.mju.lighthouseai.domain.tour_list.entity.TourList;
import com.mju.lighthouseai.domain.user.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface TourListEntityMapper {
    @Mapping(source = "constituency",target = "constituency")
    TourList toTourList(TourListCreateServiceRequestDto requestDto, User user, Constituency constituency);

}
