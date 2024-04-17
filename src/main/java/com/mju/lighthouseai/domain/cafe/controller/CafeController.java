package com.mju.lighthouseai.domain.cafe.controller;

import com.mju.lighthouseai.domain.cafe.dto.controller.CafeCreateControllerRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.controller.CafeUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.mapper.dto.CafeDtoMapper;
import com.mju.lighthouseai.domain.cafe.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class CafeController {
    private final CafeDtoMapper cafeDtoMapper;
    private final CafeService cafeService;

    @PostMapping("/cafes/create")
    public ResponseEntity<?> createCafe(
        @RequestBody CafeCreateControllerRequestDto controllerRequestDto
    ){
        CafeCreateServiceRequestDto serviceRequestDto =
            cafeDtoMapper.toCafeCreateServiceDto(controllerRequestDto);
        cafeService.createCafe(serviceRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("/cafes/{cafeId}")
    public ResponseEntity<?> updateCafe(
        @PathVariable Long cafeId,
        @RequestBody CafeUpdateControllerRequestDto controllerRequestDto
    ){
        CafeUpdateServiceRequestDto serviceRequestDto =
            cafeDtoMapper.toCafeUpdateServiceDto(controllerRequestDto);
        cafeService.updateCafe(cafeId,serviceRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("cafes/{cafeId}")
    public ResponseEntity<?> deleteCafe(
        @PathVariable Long cafeId
    ){
        cafeService.deleteCafe(cafeId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
 }
