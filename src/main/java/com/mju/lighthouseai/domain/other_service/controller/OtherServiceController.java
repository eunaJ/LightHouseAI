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
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/otherServices")
@RestController
public class OtherServiceController {
    private final OtherServiceDtoMapper otherServiceDtoMapper;
    private final OtherService otherService;

    @PostMapping("/create")
    public ResponseEntity<?> createOtherservice(
        @RequestBody OtherServiceCreateControllerRequestDto controllerRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        OtherServiceCreateServiceRequestDto serviceRequestDto =
            otherServiceDtoMapper.toOtherServiceCreateServiceDto(controllerRequestDto);
        otherService.createOtherService(serviceRequestDto,userDetails.user());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
 }
