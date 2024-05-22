package com.mju.lighthouseai.domain.shoppingmall.service;

import com.mju.lighthouseai.domain.shoppingmall.dto.service.request.ShoppingMallCreateServiceRequestDto;
import com.mju.lighthouseai.domain.shoppingmall.dto.service.request.ShoppingMallUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.shoppingmall.dto.service.response.ShoppingMallReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.user.entity.User;
import java.util.List;

public interface ShoppingMallService {
    void createShoppingMall(ShoppingMallCreateServiceRequestDto requestDto, User user);
    void updateShoppingMall(Long id, ShoppingMallUpdateServiceRequestDto requestDto,User user);
    ShoppingMallReadAllServiceResponseDto readShoppingMall(Long id);
    List<ShoppingMallReadAllServiceResponseDto> readAllShoppingMall();
    List<ShoppingMallReadAllServiceResponseDto> readConstituencyShoppingMall(Long id);

}
