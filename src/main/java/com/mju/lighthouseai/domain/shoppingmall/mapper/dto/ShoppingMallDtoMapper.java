package com.mju.lighthouseai.domain.shoppingmall.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import com.mju.lighthouseai.domain.shoppingmall.dto.controller.ShoppingMallCreateControllerRequestDto;
import com.mju.lighthouseai.domain.shoppingmall.dto.service.ShoppingMallCreateServiceRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface ShoppingMallDtoMapper {
    ShoppingMallCreateServiceRequestDto toShoppingMallCreateServiceDto(
        ShoppingMallCreateControllerRequestDto controllerRequestDto
    );

}
