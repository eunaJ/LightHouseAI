package com.mju.lighthouseai.domain.tour_list.service;

import com.mju.lighthouseai.domain.tour_list.dto.service.request.TourListCreateServiceRequestDto;
import com.mju.lighthouseai.domain.tour_list.dto.service.request.TourListUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.tour_list.dto.service.response.TourListReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.user.entity.User;
import java.util.List;

public interface TourListService {
    void createTourList(TourListCreateServiceRequestDto requestDto, User user);
    void updateTourList(Long id, TourListUpdateServiceRequestDto requestDto,User user);
    void deleteTourList(Long id,User user);
     List<TourListReadAllServiceResponseDto> readAllTourLists();
    TourListReadAllServiceResponseDto readTourList(Long id);
    List<TourListReadAllServiceResponseDto>readConstituencyTourLists(Long id);
}
