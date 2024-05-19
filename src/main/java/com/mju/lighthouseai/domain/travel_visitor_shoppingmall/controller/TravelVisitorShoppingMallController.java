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
@RequestMapping("/api/v1/travelVisitorShoppingMalls")
@RestController
public class TravelVisitorShoppingMallController {
    private final TravelVisitorShoppingMallDtoMapper travelVisitorShoppingMallDtoMapper;
    private final TravelVisitorShoppingMallServiceImpl travelVisitorShoppingMallService;
    @PostMapping("/create/{id}")
    public ResponseEntity<?> createTravelVisitorShoppingMall(
            @PathVariable Long id,
            @RequestPart TravelVisitorShoppingMallCreateControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart MultipartFile multipartFile
    )throws IOException {
        TravelVisitorShoppingMallCreateServiceRequestDto serviceRequestDto =
                travelVisitorShoppingMallDtoMapper.toTravelVisitorShoppingMallCreateServiceDto(controllerRequestDto);
        travelVisitorShoppingMallService.createTravelVisitorShoppingMall(id,serviceRequestDto, userDetails.user(), multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{travelVisitorShoppingMallId}")
    public ResponseEntity<?> updateTravelVisitorShoppingMall(
            @PathVariable Long travelVisitorShoppingMallId,
            @RequestPart TravelVisitorShoppingMallUpdateControllerRequestDto controllerRequestDto,
            @RequestPart(required = false) MultipartFile multipartFile,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    )throws IOException{
        TravelVisitorShoppingMallUpdateServiceRequestDto serviceRequestDto =
                travelVisitorShoppingMallDtoMapper.toTravelVisitorShoppingMallUpdateServiceDto(controllerRequestDto);
        travelVisitorShoppingMallService.updateTravelVisitorShoppingMall(
                travelVisitorShoppingMallId,serviceRequestDto,multipartFile,userDetails.user());
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

    @GetMapping("/{travelVisitorShoppingMallId}")
    public ResponseEntity<?> readTravelVisitorShoppingMall(
            @PathVariable Long travelVisitorShoppingMallId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelVisitorShoppingMallService.readTravelVisitorShoppingMall(
                        travelVisitorShoppingMallId));
    }

    @GetMapping("/")
    public ResponseEntity<?> readAllTravelVisitorShoppingMalls(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelVisitorShoppingMallService.readAllTravelVisitorShoppingMalls());
    }

    @GetMapping("/travel/{travelId}")
    public ResponseEntity<?> readAllTravelVisitorShoppingMallsByTravelId(
            @PathVariable Long travelId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelVisitorShoppingMallService.readAllTravelVisitorShoppingMallsByTravelId(travelId));
    }
}
