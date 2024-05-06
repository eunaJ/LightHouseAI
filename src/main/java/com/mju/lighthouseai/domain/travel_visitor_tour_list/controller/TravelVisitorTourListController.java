package com.mju.lighthouseai.domain.travel_visitor_tour_list.controller;

import com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.controller.TravelVisitorTourListCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.service.request.TravelVisitorTourListCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.mapper.dto.TravelVisitorTourListDtoMapper;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.service.impl.TravelVisitorTourListServiceImpl;
import com.mju.lighthouseai.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/api/v1/travelvisitortourlists")
@RestController
public class TravelVisitorTourListController {
    private final TravelVisitorTourListDtoMapper travelVisitorTourListDtoMapper;
    private final TravelVisitorTourListServiceImpl travelVisitorTourListService;
    @PostMapping("/create")
    public ResponseEntity<?> createTravelVisitorTourList(
            @RequestPart TravelVisitorTourListCreateControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart MultipartFile multipartFile
    )throws IOException {
        TravelVisitorTourListCreateServiceRequestDto serviceRequestDto =
                travelVisitorTourListDtoMapper.toTravelVisitorTourListCreateServiceDto(
                        controllerRequestDto);
        travelVisitorTourListService.createTravelVisitorTourList(serviceRequestDto, userDetails.user(), multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}