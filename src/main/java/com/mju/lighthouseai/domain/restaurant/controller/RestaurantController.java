package com.mju.lighthouseai.domain.restaurant.controller;

import com.mju.lighthouseai.domain.restaurant.dto.controller.RestaurantCreateControllerRequestDto;
import com.mju.lighthouseai.domain.restaurant.dto.service.request.RestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.restaurant.mapper.dto.RestaurantDtoMapper;
import com.mju.lighthouseai.domain.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("admin/restaurant/create")
    public ResponseEntity<?> createRestaurant(
        @RequestBody RestaurantCreateControllerRequestDto controllerRequestDto
    ){
        RestaurantCreateServiceRequestDto serviceRequestDto =
                restaurantDtoMapper.toRestaurantCreateServiceDto(controllerRequestDto);
        restaurantService.createRestaurant(serviceRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
 }
