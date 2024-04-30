package com.mju.lighthouseai.domain.shoppingmall.controller;

import com.mju.lighthouseai.domain.shoppingmall.dto.controller.ShoppingMallCreateControllerRequestDto;
import com.mju.lighthouseai.domain.shoppingmall.dto.controller.ShoppingMallUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.shoppingmall.dto.service.ShoppingMallCreateServiceRequestDto;
import com.mju.lighthouseai.domain.shoppingmall.dto.service.ShoppingMallUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.shoppingmall.mapper.dto.ShoppingMallDtoMapper;
import com.mju.lighthouseai.domain.shoppingmall.service.ShoppingMallService;
import com.mju.lighthouseai.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class ShoppingMallController {
    private final ShoppingMallDtoMapper shoppingMallDtoMapper;
    private final ShoppingMallService shoppingMallService;

    @PostMapping("/shoppingmalls/create")
    public ResponseEntity<?> createShoppingMall(
        @RequestBody ShoppingMallCreateControllerRequestDto controllerRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        ShoppingMallCreateServiceRequestDto serviceRequestDto =
                shoppingMallDtoMapper.toShoppingMallCreateServiceDto(controllerRequestDto);
        shoppingMallService.createShoppingMall(serviceRequestDto,userDetails.user());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("/shoppingmalls/{shoppingmallId}")
    public ResponseEntity<?> updateShoppingMall(
        @PathVariable Long shoppingmallId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody ShoppingMallUpdateControllerRequestDto controllerRequestDto
    ){
        ShoppingMallUpdateServiceRequestDto serviceRequestDto =
            shoppingMallDtoMapper.toShoppingMallUpdateServiceDto(controllerRequestDto);
        shoppingMallService.updateShoppingMall(shoppingmallId,serviceRequestDto,userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
 }
