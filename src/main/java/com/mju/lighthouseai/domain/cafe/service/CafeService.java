package com.mju.lighthouseai.domain.cafe.service;

import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeUpdateServiceRequestDto;

public interface CafeService {
    void createCafe(CafeCreateServiceRequestDto requestDto);
    void updateCafe(Long id, CafeUpdateServiceRequestDto requestDto);
    void deleteCafe(Long id);
}
