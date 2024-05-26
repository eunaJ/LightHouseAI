package com.mju.lighthouseai.domain.cafe.service;

import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.response.CafeReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.user.entity.User;
import java.util.List;

public interface CafeService {
    void createCafe(CafeCreateServiceRequestDto requestDto,User user);
    void updateCafe(Long id, CafeUpdateServiceRequestDto requestDto,User user);
    void deleteCafe(Long id,User user);
    CafeReadAllServiceResponseDto readCafe(Long id);
    List<CafeReadAllServiceResponseDto> readConstituencyCafe(Long id);
    List<CafeReadAllServiceResponseDto> readAllCafes();
}
