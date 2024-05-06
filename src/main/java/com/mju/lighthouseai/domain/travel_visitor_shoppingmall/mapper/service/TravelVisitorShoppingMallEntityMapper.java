package com.mju.lighthouseai.domain.travel_visitor_shoppingmall.mapper.service;

import com.mju.lighthouseai.domain.shoppingmall.entity.ShoppingMall;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.service.request.TravelVisitorShoppingMallCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.entity.TravelVisitorShoppingMall;
import com.mju.lighthouseai.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TravelVisitorShoppingMallEntityMapper {
    @Mapping(source = "requestDto.price", target = "price")
    @Mapping(source = "requestDto.opentime", target = "opentime")
    @Mapping(source = "requestDto.closetime", target = "closetime")
    @Mapping(source = "requestDto.location", target = "location")
    @Mapping(source = "shoppingMall", target = "shoppingMall")
    TravelVisitorShoppingMall toTravelVisitorShoppingMall(
            TravelVisitorShoppingMallCreateServiceRequestDto requestDto,
            User user, ShoppingMall shoppingMall, String image_url);
}