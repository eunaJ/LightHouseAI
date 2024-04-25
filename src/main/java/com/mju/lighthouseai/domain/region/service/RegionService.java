package com.mju.lighthouseai.domain.region.service;

import com.mju.lighthouseai.domain.region.dto.service.request.RegionCreateServiceRequestDto;
import com.mju.lighthouseai.domain.user.entity.User;

public interface RegionService {
    void createRegion(RegionCreateServiceRequestDto requestDto, User user);
}