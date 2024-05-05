package com.mju.lighthouseai.domain.travel_visitor_restaurant.controller;

import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.controller.TravelVisitorRestaurantCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.request.TravelVisitorRestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.mapper.dto.TravelVisitorRestaurantDtoMapper;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.service.impl.TravelVisitorRestaurantServiceImpl;
import com.mju.lighthouseai.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/api/v1/travelvisitorrestaurants")
@RestController
public class TravelVisitorRestaurantController {
    private final TravelVisitorRestaurantDtoMapper travelVisitorRestaurantDtoMapper;
    private final TravelVisitorRestaurantServiceImpl travelVisitorRestaurantService;
    @PostMapping("/create")
    public ResponseEntity<?> createTravelVisitorCafe(
            @RequestPart TravelVisitorRestaurantCreateControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart MultipartFile multipartFile
    )throws IOException {
        TravelVisitorRestaurantCreateServiceRequestDto serviceRequestDto =
                travelVisitorRestaurantDtoMapper.toTravelVisitorRestaurantCreateServiceDto(controllerRequestDto);
        travelVisitorRestaurantService.createTravelVisitorRestaurant(serviceRequestDto, userDetails.user(), multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
