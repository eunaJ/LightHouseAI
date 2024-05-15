package com.mju.lighthouseai.domain.travel.controller;

import com.mju.lighthouseai.domain.travel.dto.controller.TravelCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel.dto.service.request.TravelCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel.mapper.dto.TravelDtoMapper;
import com.mju.lighthouseai.domain.travel.service.TravelService;
import com.mju.lighthouseai.domain.travel.service.impl.TraveServiceImpl;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.controller.TravelVisitorCafeCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.request.TravelVisitorCafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.mapper.dto.TravelVisitorCafeDtoMapper;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.controller.TravelVisitorRestaurantCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.request.TravelVisitorRestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.entity.TravelVisitorRestaurant;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.mapper.dto.TravelVisitorRestaurantDtoMapper;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/v1/travels")
@RestController
public class TravelController {
    private final TravelDtoMapper travelDtoMapper;
    private final TravelVisitorCafeDtoMapper travelVisitorCafeDtoMapper;
    private final TravelService travelService;
    private final TravelVisitorRestaurantDtoMapper travelVisitorRestaurantDtoMapper;
    @PostMapping("/create")
    public ResponseEntity<?> createTravelVisitorCafe(
        @Valid @RequestPart(name = "travelCreateRequestDto") TravelCreateControllerRequestDto travelCreateControllerRequestDto,
        @RequestPart(name = "travelCreateImage",required = false) MultipartFile travelImage,
        @Valid @RequestPart(name = "TravelVisitorCafeCreateServiceRequestDto") List<TravelVisitorCafeCreateControllerRequestDto> TravelVisitorCafeCreateControllerRequestDto,
        @RequestPart(name = "travelVisitorCafeImage",required = false) List<MultipartFile> travelVisitorCafeImage,
        @Valid @RequestPart(name = "TravelVisitorRestaurantCreateServiceRequestDto") List<TravelVisitorRestaurantCreateControllerRequestDto> TravelVisitorRestaurantCreateControllerRequestDto,
        @RequestPart(name = "TravelVisitorRestaurantImage",required = false) List<MultipartFile> TravelVisitorRestaurantImage,
        @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        TravelCreateServiceRequestDto serviceRequestDto =
                travelDtoMapper.toTravelCreateServiceDto(travelCreateControllerRequestDto);
        List<TravelVisitorCafeCreateServiceRequestDto> TravelVisitorCafeCreateServiceRequestDtos=
            travelVisitorCafeDtoMapper.toTravelVisitorCafeCreateServiceDtos(
                TravelVisitorCafeCreateControllerRequestDto);
        List<TravelVisitorRestaurantCreateServiceRequestDto> TravelVisitorRestaurantCreateServiceRequestDtos =
            travelVisitorRestaurantDtoMapper.toTravelVisitorRestaurantCreateServiceDtos(TravelVisitorRestaurantCreateControllerRequestDto);
        travelService.createTravel(
            serviceRequestDto,
            travelImage,
            TravelVisitorCafeCreateServiceRequestDtos,
            travelVisitorCafeImage,
            TravelVisitorRestaurantCreateServiceRequestDtos,
            TravelVisitorRestaurantImage,
        userDetails.user()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
/*
    @PutMapping("/{travelVisitorCafeId}")
    public ResponseEntity<?> updateTravelVisitorCafe(
            @PathVariable Long travelVisitorCafeId,
            @RequestBody TravelUpdateControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        TravelUpdateServiceRequestDto serviceRequestDto =
                travelDtoMapper.toTravelVisitorCafeUpdateServiceDto(controllerRequestDto);
        travelVisitorCafeService.updateTravelVisitorCafe(travelVisitorCafeId,serviceRequestDto,userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{travelVisitorCafeId}")
    public ResponseEntity<?> deleteTravelVisitorCafe(
            @PathVariable Long travelVisitorCafeId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        travelVisitorCafeService.deleteTravelVisitorCafe(travelVisitorCafeId, userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{travelVisitorCafeId}")
    public ResponseEntity<?> readTravelVisitorCafe(
            @PathVariable Long travelVisitorCafeId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelVisitorCafeService.readTravelVisitorCafe(travelVisitorCafeId));
    }

    @GetMapping("/")
    public ResponseEntity<?> readAllTravelVisitorCafes(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelVisitorCafeService.readAllTravelVisitorCafes());
    }*/
}
