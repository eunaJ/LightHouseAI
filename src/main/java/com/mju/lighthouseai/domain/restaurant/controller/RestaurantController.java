package com.mju.lighthouseai.domain.restaurant.controller;

import com.mju.lighthouseai.domain.restaurant.dto.controller.RestaurantCreateControllerRequestDto;
import com.mju.lighthouseai.domain.restaurant.dto.service.RestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.restaurant.mapper.dto.RestaurantDtoMapper;
import com.mju.lighthouseai.domain.restaurant.service.RestaurantService;
import com.mju.lighthouseai.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class RestaurantController {
    private final RestaurantDtoMapper restaurantDtoMapper;
    private final RestaurantService restaurantService;

    @PostMapping("/restaurants/create")
    public ResponseEntity<?> createRestaurant(
        @RequestBody RestaurantCreateControllerRequestDto controllerRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        RestaurantCreateServiceRequestDto serviceRequestDto =
                restaurantDtoMapper.toRestaurantCreateServiceDto(controllerRequestDto);
        restaurantService.createRestaurant(serviceRequestDto,userDetails.user());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
 }
