package com.mju.lighthouseai.domain.travel_visitor_other_service.controller;

import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.controller.TravelVisitorOtherServiceCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.service.request.TravelVisitorOtherServiceCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_other_service.mapper.dto.TravelVisitorOtherServiceDtoMapper;
import com.mju.lighthouseai.domain.travel_visitor_other_service.service.impl.TravelVisitorOtherServiceImpl;
import com.mju.lighthouseai.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/api/v1/travelVisitorOtherServices")
@RestController
public class TravelVisitorOtherServiceController {
    private final TravelVisitorOtherServiceDtoMapper travelVisitorOtherServiceDtoMapper;
    private final TravelVisitorOtherServiceImpl travelVisitorOtherService;
    @PostMapping("/create")
    public ResponseEntity<?> createTravelVisitorOtherService(
            @RequestPart TravelVisitorOtherServiceCreateControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart MultipartFile multipartFile
    )throws IOException {
        TravelVisitorOtherServiceCreateServiceRequestDto serviceRequestDto =
                travelVisitorOtherServiceDtoMapper.toTravelVisitorOtherServiceCreateServiceDto(
                        controllerRequestDto);
        travelVisitorOtherService.createTravelVisitorOtherService(
                serviceRequestDto, userDetails.user(), multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}