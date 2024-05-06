package com.mju.lighthouseai.domain.travel_visitor_shoppingmall.service;

import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.service.TravelVisitorShoppingMallCreateServiceRequestDto;
import com.mju.lighthouseai.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TravelVisitorShoppingMallService {
    void createTravelVisitorShoppingMall(TravelVisitorShoppingMallCreateServiceRequestDto requestDto,
                                         User user, MultipartFile multipartFile) throws IOException;
}