package com.mju.lighthouseai.domain.other_service.controller;

import com.mju.lighthouseai.domain.other_service.dto.controller.OtherServiceCreateControllerRequestDto;
import com.mju.lighthouseai.domain.other_service.dto.service.OtherServiceCreateServiceRequestDto;
import com.mju.lighthouseai.domain.other_service.mapper.dto.OtherServiceDtoMapper;
import com.mju.lighthouseai.domain.other_service.service.OtherService;
import com.mju.lighthouseai.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class OtherServiceController {
    private final OtherServiceDtoMapper otherServiceDtoMapper;
    private final OtherService otherService;

    @PostMapping("/other_services/create")
    public ResponseEntity<?> createRestaurant(
        @RequestBody OtherServiceCreateControllerRequestDto controllerRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        OtherServiceCreateServiceRequestDto serviceRequestDto =
            otherServiceDtoMapper.toOtherServiceCreateServiceDto(controllerRequestDto);
        otherService.createOtherService(serviceRequestDto,userDetails.user());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
 }
