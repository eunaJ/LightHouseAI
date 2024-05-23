package com.mju.lighthouseai.domain.travel.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.travel.dto.service.request.TravelCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel.dto.service.response.TravelReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.travel.entity.Travel;
import com.mju.lighthouseai.domain.user.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface TravelEntityMapper {

    @Mapping(source = "constituency",target = "constituency")
    @Mapping(source = "image_url",target = "image_url")
    @Mapping(source = "user",target = "user")
    @Mapping(source = "travelFolderName",target = "folderName")
   Travel toTravel(TravelCreateServiceRequestDto requestDto,String travelFolderName,String image_url ,User user,Constituency constituency);
    default String toUserName(User user){
        return user.getNickname();
    }
    default String toCafeTitle(Cafe cafe){
        return cafe.getTitle();
    }
    List<TravelReadAllServiceResponseDto> toTravelReadAllResponseDto(List<Travel> travels);
}
