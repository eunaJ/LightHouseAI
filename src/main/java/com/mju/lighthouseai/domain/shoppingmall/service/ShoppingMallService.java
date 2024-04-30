package com.mju.lighthouseai.domain.shoppingmall.service;

import com.mju.lighthouseai.domain.shoppingmall.dto.service.ShoppingMallCreateServiceRequestDto;
import com.mju.lighthouseai.domain.user.entity.User;

public interface ShoppingMallService {
    void createShoppingMall(ShoppingMallCreateServiceRequestDto requestDto, User user);

}
