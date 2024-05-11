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
    @Mapping(source = "profile_img_url",target = "profile_img_url")
    @Mapping(source = "folderName",target = "folderName")
    User toUser(UserSignUpServiceRequestDto serviceRequestDto, UserRole userRole, String profile_img_url,String folderName);

    UserLoginResponseDto toUserLoginResponseDto(User user);
}
