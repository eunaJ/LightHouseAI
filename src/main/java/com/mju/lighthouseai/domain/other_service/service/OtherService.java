package com.mju.lighthouseai.domain.other_service.service;

import com.mju.lighthouseai.domain.other_service.dto.service.request.OtherServiceCreateServiceRequestDto;
import com.mju.lighthouseai.domain.other_service.dto.service.response.OtherServiceReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.other_service.dto.service.request.OtherServiceUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.user.entity.User;

import java.util.List;

public interface OtherService {
    void createOtherService(OtherServiceCreateServiceRequestDto requestDto, User user);

    void updateOtherService(Long id, OtherServiceUpdateServiceRequestDto requestDto, User user);

    void deleteOtherService(Long id, User user);

    List<OtherServiceReadAllServiceResponseDto> readAllOtherServices();

    OtherServiceReadAllServiceResponseDto readOtherService(Long id);
    List<OtherServiceReadAllServiceResponseDto> readConstituencyOtherServices(Long id);
}
