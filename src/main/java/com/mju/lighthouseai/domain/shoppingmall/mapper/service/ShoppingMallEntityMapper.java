package com.mju.lighthouseai.domain.shoppingmall.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.shoppingmall.dto.service.ShoppingMallCreateServiceRequestDto;
import com.mju.lighthouseai.domain.shoppingmall.entity.ShoppingMall;
import com.mju.lighthouseai.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface ShoppingMallEntityMapper {
    @Mapping(source = "constituency",target = "constituency")
    ShoppingMall toShoppingMall(ShoppingMallCreateServiceRequestDto requestDto, User user, Constituency constituency);
}
