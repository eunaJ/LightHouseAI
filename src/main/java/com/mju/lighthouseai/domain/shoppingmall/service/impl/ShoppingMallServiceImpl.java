package com.mju.lighthouseai.domain.shoppingmall.service.impl;

import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.constituency.exception.ConstituencyErrorCode;
import com.mju.lighthouseai.domain.constituency.exception.NotFoundConstituencyException;
import com.mju.lighthouseai.domain.constituency.repository.ConstituencyRepository;
import com.mju.lighthouseai.domain.shoppingmall.dto.service.ShoppingMallCreateServiceRequestDto;
import com.mju.lighthouseai.domain.shoppingmall.entity.ShoppingMall;
import com.mju.lighthouseai.domain.shoppingmall.mapper.service.ShoppingMallEntityMapper;
import com.mju.lighthouseai.domain.shoppingmall.repository.ShoppingMallRepository;
import com.mju.lighthouseai.domain.shoppingmall.service.ShoppingMallService;
import com.mju.lighthouseai.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingMallServiceImpl implements ShoppingMallService {

    private final ShoppingMallRepository shoppingMallRepository;
    private final ShoppingMallEntityMapper shoppingMallEntityMapper;
    private final ConstituencyRepository constituencyRepository;

    public void createShoppingMall(ShoppingMallCreateServiceRequestDto requestDto, User user){
        Constituency constituency = constituencyRepository.findByConstituency(requestDto.constituency_name())
            .orElseThrow(()->new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
        ShoppingMall shoppingMall = shoppingMallEntityMapper.toShoppingMall(requestDto,user,constituency);
        shoppingMallRepository.save(shoppingMall);
    }

}
