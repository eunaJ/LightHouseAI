package com.mju.lighthouseai.domain.travel_visitor_other_service.controller;

import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.controller.TravelVisitorOtherServiceCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.controller.TravelVisitorOtherServiceUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.service.request.TravelVisitorOtherServiceCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.service.request.TravelVisitorOtherServiceUpdateServiceRequestDto;
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

    @PutMapping("/{travelVisitorOtherServiceId}")
    public ResponseEntity<?> updateTravelVisitorOtherService(
            @PathVariable Long travelVisitorOtherServiceId,
            @RequestBody TravelVisitorOtherServiceUpdateControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        TravelVisitorOtherServiceUpdateServiceRequestDto serviceRequestDto =
                travelVisitorOtherServiceDtoMapper.toTravelVisitorOtherServiceUpdateServiceDto(
                        controllerRequestDto);
        travelVisitorOtherService.updateTravelVisitorOtherService(
                travelVisitorOtherServiceId,serviceRequestDto,userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{travelVisitorOtherServiceId}")
    public ResponseEntity<?> deleteTravelVisitorOtherService(
            @PathVariable Long travelVisitorOtherServiceId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        travelVisitorOtherService.deleteTravelVisitorOtherService(
                travelVisitorOtherServiceId, userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{travelVisitorOtherServiceId}")
    public ResponseEntity<?> readTravelVisitorOtherService(
            @PathVariable Long travelVisitorOtherServiceId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelVisitorOtherService.readTravelVisitorOtherService(
                        travelVisitorOtherServiceId));
    }

    @GetMapping("/")
    public ResponseEntity<?> readAllTravelVisitorOtherServices(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelVisitorOtherService.readAllTravelVisitorOtherServices());
    }
}