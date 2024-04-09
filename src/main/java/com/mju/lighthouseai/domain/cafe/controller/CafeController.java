package com.mju.lighthouseai.domain.cafe.controller;

import com.mju.lighthouseai.domain.cafe.dto.controller.CafeCreateControllerRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.mapper.dto.CafeDtoMapper;
import com.mju.lighthouseai.domain.cafe.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class CafeController {
    private final CafeDtoMapper cafeDtoMapper;
    private final CafeService cafeService;

    @PostMapping("/cafe")
    public ResponseEntity<?> createCafe(
        @RequestBody CafeCreateControllerRequestDto controllerRequestDto
    ){
        CafeCreateServiceRequestDto serviceRequestDto =
            cafeDtoMapper.toCafeCreateServiceDto(controllerRequestDto);
        cafeService.createCafe(serviceRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
 }
