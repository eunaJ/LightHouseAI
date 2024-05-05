package com.mju.lighthouseai.domain.travel_visitor_cafe.service;

import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.TravelVisitorCafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.TravelVisitorCafeUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TravelVisitorCafeService {
    void createTravelVisitorCafe(TravelVisitorCafeCreateServiceRequestDto requestDto, User user,
                                 MultipartFile multipartFile) throws IOException;
    void updateTravelVisitorCafe(Long id, TravelVisitorCafeUpdateServiceRequestDto requestDto, User user);
}