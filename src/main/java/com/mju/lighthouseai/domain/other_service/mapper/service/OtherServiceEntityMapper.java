package com.mju.lighthouseai.domain.other_service.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.other_service.dto.service.request.OtherServiceCreateServiceRequestDto;
import com.mju.lighthouseai.domain.other_service.dto.service.response.OtherServiceReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.other_service.entity.OtherServiceEntity;
import com.mju.lighthouseai.domain.region.entity.Region;
import com.mju.lighthouseai.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = SPRING)
public interface OtherServiceEntityMapper {
    @Mapping(source = "constituency",target = "constituency")
    OtherServiceEntity toOtherService(OtherServiceCreateServiceRequestDto requestDto, User user, Constituency constituency);

    default String toUserName(User user){
        return user.getNickname();
    }
    default String toConstituencyName(Constituency constituency){
        return constituency.getConstituency();
    }
    default String toConstituencyRegionName(Region region){
        return region.getRegion_name();
    }
    @Mapping(source = "user",target = "nickname")
    @Mapping(source = "constituency",target = "constituency_name")
    @Mapping(source = "constituency.region",target = "region_name")
    OtherServiceReadAllServiceResponseDto toOtherServiceReadResponseDto(OtherServiceEntity otherServiceEntity);

    List<OtherServiceReadAllServiceResponseDto> toOtherServiceReadAllResponseDto(List<OtherServiceEntity> otherServiceEntityList);
}
