package com.mju.lighthouseai.domain.user.mapper.entity;

import com.mju.lighthouseai.domain.user.dto.service.request.UserSignUpServiceRequestDto;
import com.mju.lighthouseai.domain.user.dto.service.response.UserLoginResponseDto;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.domain.user.entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = PasswordTranslator.class)
public interface UserEntityMapper {
    @Mapping(source = "serviceRequestDto.password", target = "password", qualifiedBy = EncoderPassword.class)
    @Mapping(source = "userRole", target = "role")
    User toUser(UserSignUpServiceRequestDto serviceRequestDto, UserRole userRole);

    UserLoginResponseDto toUserLoginResponseDto(User user);
}
