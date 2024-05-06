package com.mju.lighthouseai.domain.travel_visitor_other_service.service;

import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.service.request.TravelVisitorOtherServiceCreateServiceRequestDto;
import com.mju.lighthouseai.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TravelVisitorOtherService {
    void createTravelVisitorOtherService(TravelVisitorOtherServiceCreateServiceRequestDto requestDto,
                                         User user, MultipartFile multipartFile) throws IOException;
}
