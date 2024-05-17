package com.mju.lighthouseai.domain.travel_visitor_tour_list.service;

import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.service.response.TravelVisitorShoppingMallReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.service.request.TravelVisitorTourListCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.service.request.TravelVisitorTourListUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.service.response.TravelVisitorTourListReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TravelVisitorTourListService {
    void createTravelVisitorTourList(Long id,TravelVisitorTourListCreateServiceRequestDto requestDto, User user,
                                     MultipartFile multipartFile) throws IOException;

    void updateTravelVisitorTourList(Long id, TravelVisitorTourListUpdateServiceRequestDto requestDto,
                                     User user);
    void deleteTravelVisitorTourList(Long id, User user);
    TravelVisitorTourListReadAllServiceResponseDto readTravelVisitorTourList(Long id);
    List<TravelVisitorTourListReadAllServiceResponseDto> readAllTravelVisitorTourLists();
    List<TravelVisitorTourListReadAllServiceResponseDto> readAllTravelVisitorTourListsByTravelId(Long id);
}