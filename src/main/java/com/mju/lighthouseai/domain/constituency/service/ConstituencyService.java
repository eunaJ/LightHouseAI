package com.mju.lighthouseai.domain.constituency.service;

import com.mju.lighthouseai.domain.constituency.dto.service.request.ConstituencyCreateServiceRequestDto;
import com.mju.lighthouseai.domain.constituency.dto.service.request.ConstituencyUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.user.entity.User;

public interface ConstituencyService {
    void createConstituency(ConstituencyCreateServiceRequestDto requestDto, User user);
    void updateConstituency(Long id, ConstituencyUpdateServiceRequestDto requestDto);
    void deleteConstituency(Long id);
}
