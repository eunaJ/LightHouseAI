package com.mju.lighthouseai.domain.tour_list.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.mju.lighthouseai.domain.cafe.dto.service.response.CafeReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.region.entity.Region;
import com.mju.lighthouseai.domain.tour_list.dto.service.request.TourListCreateServiceRequestDto;
import com.mju.lighthouseai.domain.tour_list.dto.service.response.TourListReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.tour_list.entity.TourList;
import com.mju.lighthouseai.domain.user.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface TourListEntityMapper {
    @Mapping(source = "constituency",target = "constituency")
    TourList toTourList(TourListCreateServiceRequestDto requestDto, User user, Constituency constituency);
    default String toUserName(User user){
        return user.getNickname();
    }
    default String toConstituencyName(Constituency constituency){
        return constituency.getConstituency();
    }
    default String toConstituencyRegionName(Region region){
        return region.getRegion_name();
    }

    @Mapping(source = "user",target = "user_name")
    @Mapping(source = "constituency",target = "constituency_name")
    @Mapping(source = "constituency.region",target = "region_name")
    TourListReadAllServiceResponseDto toTourListReadResponseDto(TourList tourList);

    List<TourListReadAllServiceResponseDto> toTourListReadAllResponseDto(List<TourList> tourLists);
}
