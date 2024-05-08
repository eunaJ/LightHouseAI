package com.mju.lighthouseai.domain.shoppingmall.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.region.entity.Region;
import com.mju.lighthouseai.domain.shoppingmall.dto.service.request.ShoppingMallCreateServiceRequestDto;
import com.mju.lighthouseai.domain.shoppingmall.dto.service.response.ShoppingMallReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.shoppingmall.entity.ShoppingMall;
import com.mju.lighthouseai.domain.user.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface ShoppingMallEntityMapper {
    @Mapping(source = "constituency",target = "constituency")
    ShoppingMall toShoppingMall(ShoppingMallCreateServiceRequestDto requestDto, User user, Constituency constituency);

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
    ShoppingMallReadAllServiceResponseDto toShoppingMallReadResponseDto(ShoppingMall shoppingMall);
    List<ShoppingMallReadAllServiceResponseDto> toShoppingMallReadAllResponseDto(List<ShoppingMall> shoppingMalls);
}
