package com.mju.lighthouseai.domain.cafe.service;

import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeCreateServiceRequestDto;

public interface CafeService {
    void createCafe(CafeCreateServiceRequestDto requestDto);

}
