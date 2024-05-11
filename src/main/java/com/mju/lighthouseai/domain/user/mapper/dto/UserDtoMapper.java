package com.mju.lighthouseai.domain.user.mapper.dto;

import com.mju.lighthouseai.domain.user.dto.controller.request.UpdateUserControllerRequestDto;
import com.mju.lighthouseai.domain.user.dto.controller.request.UserLoginControllerRequestDto;
import com.mju.lighthouseai.domain.user.dto.controller.request.UserSignUpControllerRequestDto;
import com.mju.lighthouseai.domain.user.dto.service.request.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserDtoMapper {
    UserSignUpServiceRequestDto toUserSignUpServiceRequestDto(
            UserSignUpControllerRequestDto userSignUpControllerRequestDto
    );

    UserLoginServiceRequestDto toUserLoginServiceRequestDto(
            UserLoginControllerRequestDto userLoginControllerRequestDto
    );

    UpdateUserServiceRequestDto toUpdateUserServiceRequestDto(
            UpdateUserControllerRequestDto updateUserControllerRequestDto
    );
}
