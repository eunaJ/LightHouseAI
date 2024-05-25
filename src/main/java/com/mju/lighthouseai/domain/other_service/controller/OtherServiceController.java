package com.mju.lighthouseai.domain.other_service.controller;

import com.mju.lighthouseai.domain.other_service.dto.controller.OtherServiceCreateControllerRequestDto;
import com.mju.lighthouseai.domain.other_service.dto.controller.OtherServiceUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.other_service.dto.service.request.OtherServiceCreateServiceRequestDto;
import com.mju.lighthouseai.domain.other_service.dto.service.request.OtherServiceUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.other_service.mapper.dto.OtherServiceDtoMapper;
import com.mju.lighthouseai.domain.other_service.service.OtherService;
import com.mju.lighthouseai.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class OtherServiceController {
    private final OtherServiceDtoMapper otherServiceDtoMapper;
    private final OtherService otherService;

    @PostMapping("/otherServices/create")
    public ResponseEntity<?> createOtherService(
            @RequestBody OtherServiceCreateControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        OtherServiceCreateServiceRequestDto serviceRequestDto =
                otherServiceDtoMapper.toOtherServiceCreateServiceDto(controllerRequestDto);
        otherService.createOtherService(serviceRequestDto,userDetails.user());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/otherServices/{otherServiceId}")
    public ResponseEntity<?> updateOtherService(
            @PathVariable Long otherServiceId,
            @RequestBody OtherServiceUpdateControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        OtherServiceUpdateServiceRequestDto serviceRequestDto =
                otherServiceDtoMapper.toOtherServiceUpdateServiceDto(controllerRequestDto);
        otherService.updateOtherService(otherServiceId, serviceRequestDto, userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/otherServices/{otherServiceId}")
    public ResponseEntity<?> deleteOtherService(
            @PathVariable Long otherServiceId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        otherService.deleteOtherService(otherServiceId, userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/otherServices")
    public ResponseEntity<?> readAllOtherServices(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(otherService.readAllOtherServices());
    }

    @GetMapping("/otherServices/{otherServiceId}")
    public ResponseEntity<?> readOtherService(
            @PathVariable Long otherServiceId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(otherService.readOtherService(otherServiceId));
    }
    @GetMapping("/{constituency_id}/otherServices")
    public ResponseEntity<?> readConstituencyOtherServices(
        @PathVariable Long constituency_id
    ){
        return ResponseEntity.status(HttpStatus.OK)
            .body(otherService.readConstituencyOtherServices(constituency_id));
    }
 }
