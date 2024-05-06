package com.mju.lighthouseai.domain.travel_visitor_shoppingmall.controller;

import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.controller.TravelVisitorShoppingMallCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.controller.TravelVisitorShoppingMallUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.service.request.TravelVisitorShoppingMallCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.service.request.TravelVisitorShoppingMallUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.mapper.dto.TravelVisitorShoppingMallDtoMapper;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.service.impl.TravelVisitorShoppingMallServiceImpl;
import com.mju.lighthouseai.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/api/v1/travelvisitorshoppingmalls")
@RestController
public class TravelVisitorShoppingMallController {
    private final TravelVisitorShoppingMallDtoMapper travelVisitorShoppingMallDtoMapper;
    private final TravelVisitorShoppingMallServiceImpl travelVisitorShoppingMallService;
    @PostMapping("/create")
    public ResponseEntity<?> createTravelVisitorShoppingMall(
            @RequestPart TravelVisitorShoppingMallCreateControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart MultipartFile multipartFile
    )throws IOException {
        TravelVisitorShoppingMallCreateServiceRequestDto serviceRequestDto =
                travelVisitorShoppingMallDtoMapper.toTravelVisitorShoppingMallCreateServiceDto(controllerRequestDto);
        travelVisitorShoppingMallService.createTravelVisitorShoppingMall(serviceRequestDto, userDetails.user(), multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{travelVisitorShoppingMallId}")
    public ResponseEntity<?> updateTravelVisitorShoppingMall(
            @PathVariable Long travelVisitorShoppingMallId,
            @RequestBody TravelVisitorShoppingMallUpdateControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        TravelVisitorShoppingMallUpdateServiceRequestDto serviceRequestDto =
                travelVisitorShoppingMallDtoMapper.toTravelVisitorShoppingMallUpdateServiceDto(controllerRequestDto);
        travelVisitorShoppingMallService.updateTravelVisitorShoppingMall(
                travelVisitorShoppingMallId,serviceRequestDto,userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{travelVisitorShoppingMallId}")
    public ResponseEntity<?> deleteTravelVisitorShoppingMall(
            @PathVariable Long travelVisitorShoppingMallId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        travelVisitorShoppingMallService.deleteTravelVisitorShoppingMall(travelVisitorShoppingMallId, userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
