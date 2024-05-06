package com.mju.lighthouseai.domain.travel_visitor_tour_list.service;

import com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.service.request.TravelVisitorTourListCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.service.request.TravelVisitorTourListUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TravelVisitorTourListService {
    void createTravelVisitorTourList(TravelVisitorTourListCreateServiceRequestDto requestDto, User user,
                                     MultipartFile multipartFile) throws IOException;

    void updateTravelVisitorTourList(Long id, TravelVisitorTourListUpdateServiceRequestDto requestDto,
                                     User user);
    void deleteTravelVisitorTourList(Long id, User user);
}