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

    @PutMapping("/restaurants/{restaurantId}")
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

    @DeleteMapping("/restaurants/{restaurantId}")
    public ResponseEntity<?> deleteRestaurant(

            @PathVariable Long restaurantId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        restaurantService.deleteRestaurant(restaurantId, userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/restaurants")
    public ResponseEntity<?> readAllRestaurants(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(restaurantService.readAllRestaurants());
    }

    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<?> readRestaurant(
            @PathVariable Long restaurantId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(restaurantService.readRestaurant(restaurantId));
    }
    @GetMapping("/{constituency_id}/restaurants")
    public ResponseEntity<?> readConstituencyRestaurants(
        @PathVariable Long  constituency_id
    ){
        return ResponseEntity.status(HttpStatus.OK)
            .body(restaurantService.readConstituencyRestaurants(constituency_id));
    }
 }
