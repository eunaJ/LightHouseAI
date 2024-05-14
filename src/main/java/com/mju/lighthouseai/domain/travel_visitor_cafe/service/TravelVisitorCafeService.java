package com.mju.lighthouseai.domain.travel_visitor_cafe.service;

import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.request.TravelVisitorCafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.request.TravelVisitorCafeUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.response.TravelVisitorCafeReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TravelVisitorCafeService {
    void createTravelVisitorCafe(TravelVisitorCafeCreateServiceRequestDto requestDto, User user,
                                 Long travelId, MultipartFile multipartFile) throws IOException;
    void updateTravelVisitorCafe(Long id, TravelVisitorCafeUpdateServiceRequestDto requestDto, User user);
    void deleteTravelVisitorCafe(Long id, User user);
    TravelVisitorCafeReadAllServiceResponseDto readTravelVisitorCafe(Long id);
    List<TravelVisitorCafeReadAllServiceResponseDto> readAllTravelVisitorCafes();
}