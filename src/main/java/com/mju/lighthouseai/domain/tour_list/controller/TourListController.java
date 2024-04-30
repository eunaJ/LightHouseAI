package com.mju.lighthouseai.domain.tour_list.controller;


import com.mju.lighthouseai.domain.tour_list.dto.controller.TourListCreateControllerRequestDto;
import com.mju.lighthouseai.domain.tour_list.dto.controller.TourListUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.tour_list.dto.service.TourListCreateServiceRequestDto;
import com.mju.lighthouseai.domain.tour_list.dto.service.TourListUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.tour_list.mapper.dto.TourListDtoMapper;
import com.mju.lighthouseai.domain.tour_list.service.TourListService;
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
public class TourListController {
    private final TourListDtoMapper tourListDtoMapper;
    private final TourListService tourListService;

    @PostMapping("/tourList/create")
    public ResponseEntity<?> createTourList(
        @RequestBody TourListCreateControllerRequestDto controllerRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        TourListCreateServiceRequestDto serviceRequestDto =
            tourListDtoMapper.toTourListCreateServiceDto(controllerRequestDto);
        tourListService.createTourList(serviceRequestDto,userDetails.user());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("/tourLists/{tourListId}")
    public ResponseEntity<?> updateTourList(
        @PathVariable Long tourListId,
        @RequestBody TourListUpdateControllerRequestDto controllerRequestDto
    ){
        TourListUpdateServiceRequestDto serviceRequestDto =
            tourListDtoMapper.toTourListUpdateServiceDto(controllerRequestDto);
        tourListService.updateTourList(tourListId,serviceRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
/*    @GetMapping("/cafes")
    public ResponseEntity<?> readAllCafes(){
        return ResponseEntity.status(HttpStatus.OK)
            .body(cafeService.readAllCafes());
    }
    @DeleteMapping("/cafes/{cafeId}")
    public ResponseEntity<?> deleteCafe(
        @PathVariable Long cafeId
    ){
        cafeService.deleteCafe(cafeId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }*/
 }
