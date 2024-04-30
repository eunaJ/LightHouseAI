package com.mju.lighthouseai.domain.tour_list.service;

import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.response.CafeReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.tour_list.dto.service.TourListCreateServiceRequestDto;
import com.mju.lighthouseai.domain.tour_list.dto.service.TourListUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.user.entity.User;
import java.util.List;

public interface TourListService {
    void createTourList(TourListCreateServiceRequestDto requestDto, User user);
    void updateTourList(Long id, TourListUpdateServiceRequestDto requestDto);
/*
    void deleteCafe(Long id);
    List<CafeReadAllServiceResponseDto> readAllCafes();*/
}
