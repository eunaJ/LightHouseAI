package com.mju.lighthouseai.domain.cafe.controller;

import com.mju.lighthouseai.domain.cafe.dto.controller.CafeCreateControllerRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.controller.CafeUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.mapper.dto.CafeDtoMapper;
import com.mju.lighthouseai.domain.cafe.service.CafeService;
import com.mju.lighthouseai.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class CafeController {
    private final CafeDtoMapper cafeDtoMapper;
    private final CafeService cafeService;

    @PostMapping("/cafes/create")
    public ResponseEntity<?> createCafe(
        @RequestBody CafeCreateControllerRequestDto controllerRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        CafeCreateServiceRequestDto serviceRequestDto =
            cafeDtoMapper.toCafeCreateServiceDto(controllerRequestDto);
        cafeService.createCafe(serviceRequestDto,userDetails.user());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("/cafes/{cafeId}")
    public ResponseEntity<?> updateCafe(
        @PathVariable Long cafeId,
        @RequestBody CafeUpdateControllerRequestDto controllerRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        CafeUpdateServiceRequestDto serviceRequestDto =
            cafeDtoMapper.toCafeUpdateServiceDto(controllerRequestDto);
        cafeService.updateCafe(cafeId,serviceRequestDto,userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("/cafes/{cafeId}")
    public ResponseEntity<?> deleteCafe(
        @PathVariable Long cafeId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        cafeService.deleteCafe(cafeId,userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/cafes")
    public ResponseEntity<?> readAllCafes(){
        return ResponseEntity.status(HttpStatus.OK)
            .body(cafeService.readAllCafes());
    }
    @GetMapping("/cafes/{cafeId}")
    public ResponseEntity<?> readCafe(
        @PathVariable Long cafeId
    ){
        return ResponseEntity.status(HttpStatus.OK)
            .body(cafeService.readCafe(cafeId));
    }
    @GetMapping("/{constituency_id}/cafes")
    public ResponseEntity<?> readConstituencyCafe(
        @PathVariable Long constituency_id
    ){
        return ResponseEntity.status(HttpStatus.OK)
            .body(cafeService.readConstituencyCafe(constituency_id));
    }
 }
