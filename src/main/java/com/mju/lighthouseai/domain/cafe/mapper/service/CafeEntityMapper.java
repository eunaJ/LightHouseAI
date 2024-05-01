package com.mju.lighthouseai.domain.cafe.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.response.CafeReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.region.entity.Region;
import com.mju.lighthouseai.domain.user.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface CafeEntityMapper {
    @Mapping(source = "constituency",target = "constituency")
    Cafe tocafe(CafeCreateServiceRequestDto requestDto, User user,Constituency constituency);
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
    CafeReadAllServiceResponseDto toCafeReadResponseDto(Cafe cafe);

    List<CafeReadAllServiceResponseDto> toCafeReadAllResponseDto(List<Cafe> cafes);
}
