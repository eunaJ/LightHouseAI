package com.mju.lighthouseai.domain.travel_visitor_cafe.controller;

import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.controller.TravelVisitorCafeCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.controller.TravelVisitorCafeUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.request.TravelVisitorCafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.request.TravelVisitorCafeUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.mapper.dto.TravelVisitorCafeDtoMapper;
import com.mju.lighthouseai.domain.travel_visitor_cafe.service.impl.TravelVisitorCafeServiceImpl;
import com.mju.lighthouseai.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/api/v1/travelVisitorCafes")
@RestController
public class TravelVisitorCafeController {
    private final TravelVisitorCafeDtoMapper travelVisitorCafeDtoMapper;
    private final TravelVisitorCafeServiceImpl travelVisitorCafeService;
    @PostMapping("/create/{travelId}")
    public ResponseEntity<?> createTravelVisitorCafe(
            @RequestPart TravelVisitorCafeCreateControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long travelId,
            @RequestPart(required = false) MultipartFile multipartFile
    )throws IOException {
        TravelVisitorCafeCreateServiceRequestDto serviceRequestDto =
                travelVisitorCafeDtoMapper.toTravelVisitorCafeCreateServiceDto(controllerRequestDto);
        travelVisitorCafeService.createTravelVisitorCafe(serviceRequestDto, userDetails.user(),travelId, multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{travelVisitorCafeId}")
    public ResponseEntity<?> updateTravelVisitorCafe(
            @PathVariable Long travelVisitorCafeId,
            @RequestPart TravelVisitorCafeUpdateControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart(required = false)MultipartFile multipartFile
    )throws IOException{
        TravelVisitorCafeUpdateServiceRequestDto serviceRequestDto =
                travelVisitorCafeDtoMapper.toTravelVisitorCafeUpdateServiceDto(controllerRequestDto);
        travelVisitorCafeService.updateTravelVisitorCafe(travelVisitorCafeId,serviceRequestDto,multipartFile,userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{travelVisitorCafeId}")
    public ResponseEntity<?> deleteTravelVisitorCafe(
            @PathVariable Long travelVisitorCafeId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        travelVisitorCafeService.deleteTravelVisitorCafe(travelVisitorCafeId, userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{travelVisitorCafeId}")
    public ResponseEntity<?> readTravelVisitorCafe(
            @PathVariable Long travelVisitorCafeId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelVisitorCafeService.readTravelVisitorCafe(travelVisitorCafeId));
    }

    @GetMapping("/")
    public ResponseEntity<?> readAllTravelVisitorCafes(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelVisitorCafeService.readAllTravelVisitorCafes());
    }

    @GetMapping("/travel/{travelId}")
    public ResponseEntity<?> readAllTravelVisitorCafesByTravelId(
            @PathVariable Long travelId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelVisitorCafeService.readAllTravelVisitorCafesByTravelId(travelId));
    }
}
