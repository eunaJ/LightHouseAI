package com.mju.lighthouseai.domain.travel_visitor_cafe.controller;

import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.controller.TravelVisitorCafeCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.TravelVisitorCafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.mapper.dto.TravelVisitorCafeDtoMapper;
import com.mju.lighthouseai.domain.travel_visitor_cafe.service.impl.TravelVisitorCafeServiceImpl;
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
@RequestMapping("/api/v1/travelvisitorcafe")
@RestController
public class TravelVisitorCafeController {
    private final TravelVisitorCafeDtoMapper travelVisitorCafeDtoMapper;
    private final TravelVisitorCafeServiceImpl travelVisitorCafeService;
    @PostMapping("/create")
    public ResponseEntity<?> createTravelVisitorCafe(
            @RequestPart TravelVisitorCafeCreateControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart MultipartFile multipartFile
    )throws IOException {
        TravelVisitorCafeCreateServiceRequestDto serviceRequestDto =
                travelVisitorCafeDtoMapper.toTravelVisitorCafeCreateServiceDto(controllerRequestDto);
        travelVisitorCafeService.createTravelVisitorCafe(serviceRequestDto, userDetails.user(), multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
