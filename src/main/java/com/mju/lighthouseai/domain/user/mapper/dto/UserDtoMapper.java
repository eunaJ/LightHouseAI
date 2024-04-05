package com.mju.lighthouseai.domain.user.mapper.dto;

import com.mju.lighthouseai.domain.user.dto.controller.UserSignUpControllerRequestDto;
import com.mju.lighthouseai.domain.user.dto.service.UserSignUpServiceRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserDtoMapper {
    UserSignUpServiceRequestDto toUserSignUpServiceRequestDto(
            UserSignUpControllerRequestDto userSignUpControllerRequestDto
    );
}
