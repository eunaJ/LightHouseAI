package com.mju.lighthouseai.domain.travel_visitor_restaurant.controller;

import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.controller.TravelVisitorRestaurantCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.controller.TravelVisitorRestaurantUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.request.TravelVisitorRestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.request.TravelVisitorRestaurantUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.mapper.dto.TravelVisitorRestaurantDtoMapper;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.service.impl.TravelVisitorRestaurantServiceImpl;
import com.mju.lighthouseai.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/api/v1/travelVisitorRestaurants")
@RestController
public class TravelVisitorRestaurantController {
    private final TravelVisitorRestaurantDtoMapper travelVisitorRestaurantDtoMapper;
    private final TravelVisitorRestaurantServiceImpl travelVisitorRestaurantService;
    @PostMapping("/create")
    public ResponseEntity<?> createTravelVisitorCafe(
            @PathVariable Long id,
            @RequestPart TravelVisitorRestaurantCreateControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart MultipartFile multipartFile
    )throws IOException {
        TravelVisitorRestaurantCreateServiceRequestDto serviceRequestDto =
                travelVisitorRestaurantDtoMapper.toTravelVisitorRestaurantCreateServiceDto(controllerRequestDto);
        travelVisitorRestaurantService.createTravelVisitorRestaurant(id,serviceRequestDto, userDetails.user(), multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{travelVisitorRestaurantId}")
    public ResponseEntity<?> updateTravelVisitorRestaurant(
            @PathVariable Long travelVisitorRestaurantId,
            @RequestBody TravelVisitorRestaurantUpdateControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        TravelVisitorRestaurantUpdateServiceRequestDto serviceRequestDto =
                travelVisitorRestaurantDtoMapper.toTravelVisitorRestaurantUpdateServiceDto(controllerRequestDto);
        travelVisitorRestaurantService.updateTravelVisitorRestaurant(travelVisitorRestaurantId,serviceRequestDto,userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{travelVisitorRestaurantId}")
    public ResponseEntity<?> deleteTravelVisitorRestaurant(
            @PathVariable Long travelVisitorRestaurantId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        travelVisitorRestaurantService.deleteTravelVisitorRestaurant(travelVisitorRestaurantId, userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{travelVisitorRestaurantId}")
    public ResponseEntity<?> readTravelVisitorRestaurant(
            @PathVariable Long travelVisitorRestaurantId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelVisitorRestaurantService.readTravelVisitorRestaurant(travelVisitorRestaurantId));
    }

    @GetMapping("/")
    public ResponseEntity<?> readAllTravelVisitorRestaurant(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelVisitorRestaurantService.readAllTravelVisitorRestaurants());
    }

    @GetMapping("/travel/{travelId}")
    public ResponseEntity<?> readAllTravelVisitorRestaurantsByTravelId(
            @PathVariable Long travelId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelVisitorRestaurantService.readAllTravelVisitorRestaurantsByTravelId(travelId));
    }
}
