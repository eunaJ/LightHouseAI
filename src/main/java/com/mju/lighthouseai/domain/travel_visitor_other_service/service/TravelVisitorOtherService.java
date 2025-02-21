package com.mju.lighthouseai.domain.travel_visitor_other_service.service;

import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.response.TravelVisitorCafeReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.service.request.TravelVisitorOtherServiceCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.service.request.TravelVisitorOtherServiceUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.service.response.TravelVisitorOtherServiceReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TravelVisitorOtherService {
    void createTravelVisitorOtherService(
        Long id,
        TravelVisitorOtherServiceCreateServiceRequestDto requestDto,
                                         User user, MultipartFile multipartFile) throws IOException;
    void updateTravelVisitorOtherService(
            Long id, TravelVisitorOtherServiceUpdateServiceRequestDto requestDto, MultipartFile multipartFile,User user) throws IOException;
    void deleteTravelVisitorOtherService(Long id, User user);
    TravelVisitorOtherServiceReadAllServiceResponseDto readTravelVisitorOtherService(Long id);
    List<TravelVisitorOtherServiceReadAllServiceResponseDto> readAllTravelVisitorOtherServices();
    List<TravelVisitorOtherServiceReadAllServiceResponseDto> readAllTravelVisitorOtherServicesByTravelId(Long id);
}