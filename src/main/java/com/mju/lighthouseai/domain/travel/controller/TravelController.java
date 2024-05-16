package com.mju.lighthouseai.domain.travel.controller;

import com.mju.lighthouseai.domain.travel.dto.controller.TravelCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel.dto.controller.TravelUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.travel.dto.service.request.TravelCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel.dto.service.request.TravelUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.travel.mapper.dto.TravelDtoMapper;
import com.mju.lighthouseai.domain.travel.service.TravelService;
import com.mju.lighthouseai.domain.travel.service.impl.TraveServiceImpl;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.controller.TravelVisitorCafeCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.request.TravelVisitorCafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.mapper.dto.TravelVisitorCafeDtoMapper;
import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.controller.TravelVisitorOtherServiceCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.service.request.TravelVisitorOtherServiceCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_other_service.mapper.dto.TravelVisitorOtherServiceDtoMapper;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.controller.TravelVisitorRestaurantCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.request.TravelVisitorRestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.entity.TravelVisitorRestaurant;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.mapper.dto.TravelVisitorRestaurantDtoMapper;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.controller.TravelVisitorShoppingMallCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.service.request.TravelVisitorShoppingMallCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.mapper.dto.TravelVisitorShoppingMallDtoMapper;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.mapper.service.TravelVisitorShoppingMallEntityMapper;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.controller.TravelVisitorTourListCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.service.request.TravelVisitorTourListCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.mapper.dto.TravelVisitorTourListDtoMapper;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    private final TravelVisitorShoppingMallDtoMapper travelVisitorShoppingMallDtoMapper;
    private final TravelVisitorTourListDtoMapper travelVisitorTourListDtoMapper;
    private final TravelVisitorOtherServiceDtoMapper travelVisitorOtherServiceDtoMapper;
    @PostMapping("/create")
    public ResponseEntity<?> createTravelVisitorCafe(
        @Valid @RequestPart(name = "travelCreateRequestDto") TravelCreateControllerRequestDto travelCreateControllerRequestDto,
        @RequestPart(name = "travelCreateImage",required = false) MultipartFile travelImage,
        @Valid @RequestPart(name = "TravelVisitorCafeCreateServiceRequestDto") List<TravelVisitorCafeCreateControllerRequestDto> TravelVisitorCafeCreateControllerRequestDto,
        @RequestPart(name = "travelVisitorCafeImage",required = false) List<MultipartFile> travelVisitorCafeImage,
        @Valid @RequestPart(name = "TravelVisitorRestaurantCreateServiceRequestDto") List<TravelVisitorRestaurantCreateControllerRequestDto> TravelVisitorRestaurantCreateControllerRequestDto,
        @RequestPart(name = "TravelVisitorRestaurantImage",required = false) List<MultipartFile> TravelVisitorRestaurantImage,
        @Valid @RequestPart(name = "TravelVisitorShoppingMallCreateServiceRequestDto") List<TravelVisitorShoppingMallCreateControllerRequestDto> TravelVisitorShoppingMallCreateControllerRequestDto,
        @RequestPart(name = "TravelVisitorShoppingMallImage",required = false) List<MultipartFile> TravelVisitorShoppingMallImage,
        @Valid @RequestPart(name = "TravelVisitorTourListCreateServiceRequestDto") List<TravelVisitorTourListCreateControllerRequestDto> TravelVisitorTourListCreateControllerRequestDto,
        @RequestPart(name = "TravelVisitorTourListImage",required = false) List<MultipartFile> TravelVisitorTourListImage,
        @Valid @RequestPart(name = "TravelVisitorOtherServiceCreateServiceRequestDto") List<TravelVisitorOtherServiceCreateControllerRequestDto> TravelVisitorOtherserviceCreateControllerRequestDto,
        @RequestPart(name = "TravelVisitorOtherServiceImage",required = false) List<MultipartFile> TravelVisitorOtherServiceImage,
        @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        TravelCreateServiceRequestDto serviceRequestDto =
                travelDtoMapper.toTravelCreateServiceDto(travelCreateControllerRequestDto);
        List<TravelVisitorCafeCreateServiceRequestDto> TravelVisitorCafeCreateServiceRequestDtos=
            travelVisitorCafeDtoMapper.toTravelVisitorCafeCreateServiceDtos(
                TravelVisitorCafeCreateControllerRequestDto);
        List<TravelVisitorRestaurantCreateServiceRequestDto> TravelVisitorRestaurantCreateServiceRequestDtos =
            travelVisitorRestaurantDtoMapper.toTravelVisitorRestaurantCreateServiceDtos(TravelVisitorRestaurantCreateControllerRequestDto);
        List<TravelVisitorShoppingMallCreateServiceRequestDto> TravelVisitorShoppingMallCreateServiceRequestDtos =
            travelVisitorShoppingMallDtoMapper.toTravelVisitorShoppingMallCreateServiceDtos(TravelVisitorShoppingMallCreateControllerRequestDto);
        List<TravelVisitorTourListCreateServiceRequestDto> TravelVisitorTourListCreateServiceRequestDtos =
            travelVisitorTourListDtoMapper.toTravelVisitorTourListCreateServiceDtos(TravelVisitorTourListCreateControllerRequestDto);
        List<TravelVisitorOtherServiceCreateServiceRequestDto> TravelVisitorOtherServiceCreateServiceRequestDtos =
            travelVisitorOtherServiceDtoMapper.toTravelVisitorOtherServiceCreateServiceDto(TravelVisitorOtherserviceCreateControllerRequestDto);
            travelService.createTravel(
            serviceRequestDto,
            travelImage,
            TravelVisitorCafeCreateServiceRequestDtos,
            travelVisitorCafeImage,
            TravelVisitorRestaurantCreateServiceRequestDtos,
            TravelVisitorRestaurantImage,
            TravelVisitorShoppingMallCreateServiceRequestDtos,
            TravelVisitorShoppingMallImage,
            TravelVisitorTourListCreateServiceRequestDtos,
            TravelVisitorTourListImage,
            TravelVisitorOtherServiceCreateServiceRequestDtos,
            TravelVisitorOtherServiceImage,
            userDetails.user()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/{travelId}")
    public ResponseEntity<?> deleteTravel(
        @PathVariable Long travelId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        travelService.deleteTravel(travelId, userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{travelId}")
    public ResponseEntity<?> updateTravel(
            @PathVariable Long travelId,
            @RequestPart TravelUpdateControllerRequestDto controllerRequestDto,
            @RequestPart(required = false) MultipartFile multipartFile,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    )throws IOException
    {
        TravelUpdateServiceRequestDto serviceRequestDto =
                travelDtoMapper.toTravelUpdateServiceDto(controllerRequestDto);
        travelService.updateTravel(travelId,serviceRequestDto,userDetails.user(),multipartFile);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

/*
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
