package com.mju.lighthouseai.domain.constituency.controller;

import com.mju.lighthouseai.domain.constituency.dto.controller.ConstituencyCreateControllerRequestDto;
import com.mju.lighthouseai.domain.constituency.dto.controller.ConstituencyUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.constituency.dto.service.request.ConstituencyCreateServiceRequestDto;
import com.mju.lighthouseai.domain.constituency.dto.service.request.ConstituencyUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.constituency.mapper.dto.ConstituencyDtoMapper;
import com.mju.lighthouseai.domain.constituency.service.ConstituencyService;
import com.mju.lighthouseai.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@RestController
public class ConstituencyController {
    private final ConstituencyDtoMapper constituencyDtoMapper;
    private final ConstituencyService constituencyService;

    @PostMapping("/constituencies/create")
    public ResponseEntity<?> createConstituency(
            @RequestBody ConstituencyCreateControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        ConstituencyCreateServiceRequestDto serviceRequestDto =
                constituencyDtoMapper.toConstituencyCreateServiceDto(controllerRequestDto);
        constituencyService.createConstituency(serviceRequestDto, userDetails.user());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/constituencies/{constituencyId}")
    public ResponseEntity<?> updateConstituency(
            @PathVariable Long constituencyId,
            @RequestBody ConstituencyUpdateControllerRequestDto controllerRequestDto
    ){
        ConstituencyUpdateServiceRequestDto serviceRequestDto =
                constituencyDtoMapper.toConstituencyUpdateServiceDto(controllerRequestDto);
        constituencyService.updateConstituency(constituencyId, serviceRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
