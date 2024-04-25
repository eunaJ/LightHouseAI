package com.mju.lighthouseai.domain.region.controller;

import com.mju.lighthouseai.domain.region.dto.controller.RegionCreateControllerRequestDto;
import com.mju.lighthouseai.domain.region.dto.service.request.RegionCreateServiceRequestDto;
import com.mju.lighthouseai.domain.region.mapper.dto.RegionDtoMapper;
import com.mju.lighthouseai.domain.region.service.RegionService;
import com.mju.lighthouseai.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class RegionController {
    private final RegionDtoMapper regionDtoMapper;
    private final RegionService regionService;

    @PostMapping("/regions/create")
    public ResponseEntity<?> createRegion(
            @RequestBody RegionCreateControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        RegionCreateServiceRequestDto serviceRequestDto =
                regionDtoMapper.toRegionCreateServiceDto(controllerRequestDto);
        regionService.createRegion(serviceRequestDto,userDetails.user());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
