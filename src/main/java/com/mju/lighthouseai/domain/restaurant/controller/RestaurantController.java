package com.mju.lighthouseai.domain.restaurant.controller;

import com.mju.lighthouseai.domain.restaurant.dto.controller.RestaurantCreateControllerRequestDto;
import com.mju.lighthouseai.domain.restaurant.dto.controller.RestaurantUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.restaurant.dto.service.request.RestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.restaurant.dto.service.request.RestaurantUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.restaurant.mapper.dto.RestaurantDtoMapper;
import com.mju.lighthouseai.domain.restaurant.service.RestaurantService;
import com.mju.lighthouseai.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurants")
@RestController
public class RestaurantController {
    private final RestaurantDtoMapper restaurantDtoMapper;
    private final RestaurantService restaurantService;

    @PostMapping("/create")
    public ResponseEntity<?> createRestaurant(
        @RequestBody RestaurantCreateControllerRequestDto controllerRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        RestaurantCreateServiceRequestDto serviceRequestDto =
                restaurantDtoMapper.toRestaurantCreateServiceDto(controllerRequestDto);
        restaurantService.createRestaurant(serviceRequestDto,userDetails.user());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<?> updateRestaurant(
            @PathVariable Long restaurantId,
            @RequestBody RestaurantUpdateControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        RestaurantUpdateServiceRequestDto serviceRequestDto =
                restaurantDtoMapper.toRestaurantUpdateServiceDto(controllerRequestDto);
        restaurantService.updateRestaurant(restaurantId, serviceRequestDto, userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/")
    public ResponseEntity<?> readAllRestaurant(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(restaurantService.readAllRestaurants());
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<?> deleteRestaurant(
            @PathVariable Long restaurantId
    ){
        restaurantService.deleteRestaurant(restaurantId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
 }
