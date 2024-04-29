package com.mju.lighthouseai.domain.cafe.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.response.CafeReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.user.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface CafeEntityMapper {
    Cafe tocafe(CafeCreateServiceRequestDto requestDto, User user);
    default String toUserName(User user){
        return user.getNickname();
    }
    @Mapping(source = "user",target = "user_name")
    CafeReadAllServiceResponseDto toCafeReadResponseDto(Cafe cafe);

    List<CafeReadAllServiceResponseDto> toCafeReadAllResponseDto(List<Cafe> cafes);
}
