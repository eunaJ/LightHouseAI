package com.mju.lighthouseai.domain.other_service.service;

import com.mju.lighthouseai.domain.other_service.dto.service.OtherServiceCreateServiceRequestDto;
import com.mju.lighthouseai.domain.restaurant.dto.service.RestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.user.entity.User;

public interface OtherService {
    void createOtherService(OtherServiceCreateServiceRequestDto requestDto, User user);
}
