package com.mju.lighthouseai.domain.user.mapper.entity;

import com.mju.lighthouseai.domain.user.dto.service.UserSignUpServiceRequestDto;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.domain.user.entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserEntityMapper {
    @Mapping(source = "userRole", target = "role")
    User toUser(UserSignUpServiceRequestDto serviceRequestDto, UserRole userRole);
}
