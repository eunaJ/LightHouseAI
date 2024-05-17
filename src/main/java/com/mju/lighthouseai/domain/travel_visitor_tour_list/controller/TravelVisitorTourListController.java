package com.mju.lighthouseai.domain.travel_visitor_tour_list.controller;

import com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.controller.TravelVisitorTourListCreateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.controller.TravelVisitorTourListUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.service.request.TravelVisitorTourListCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.service.request.TravelVisitorTourListUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.mapper.dto.TravelVisitorTourListDtoMapper;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.service.impl.TravelVisitorTourListServiceImpl;
import com.mju.lighthouseai.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/api/v1/travelVisitorTourLists")
@RestController
public class TravelVisitorTourListController {
    private final TravelVisitorTourListDtoMapper travelVisitorTourListDtoMapper;
    private final TravelVisitorTourListServiceImpl travelVisitorTourListService;
    @PostMapping("/create")
    public ResponseEntity<?> createTravelVisitorTourList(
            @PathVariable Long id,
            @RequestPart TravelVisitorTourListCreateControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart MultipartFile multipartFile
    )throws IOException {
        TravelVisitorTourListCreateServiceRequestDto serviceRequestDto =
                travelVisitorTourListDtoMapper.toTravelVisitorTourListCreateServiceDto(
                        controllerRequestDto);
        travelVisitorTourListService.createTravelVisitorTourList(id,serviceRequestDto, userDetails.user(), multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{travelVisitorTourListId}")
    public ResponseEntity<?> updateTravelVisitorTourList(
            @PathVariable Long travelVisitorTourListId,
            @RequestBody TravelVisitorTourListUpdateControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        TravelVisitorTourListUpdateServiceRequestDto serviceRequestDto =
                travelVisitorTourListDtoMapper.toTravelVisitorTourListUpdateServiceDto(controllerRequestDto);
        travelVisitorTourListService.updateTravelVisitorTourList(
                travelVisitorTourListId,serviceRequestDto,userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{travelVisitorTourListId}")
    public ResponseEntity<?> deleteTravelVisitorTourList(
            @PathVariable Long travelVisitorTourListId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        travelVisitorTourListService.deleteTravelVisitorTourList(
                travelVisitorTourListId, userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{travelVisitorTourListId}")
    public ResponseEntity<?> readTravelVisitorTourList(
            @PathVariable Long travelVisitorTourListId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelVisitorTourListService.readTravelVisitorTourList(
                        travelVisitorTourListId));
    }

    @GetMapping("/")
    public ResponseEntity<?> readAllTravelVisitorTourLists(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelVisitorTourListService.readAllTravelVisitorTourLists());
    }

    @GetMapping("/travel/{travelId}")
    public ResponseEntity<?> readAllTravelVisitorTourListsByTravelId(
            @PathVariable Long travelId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelVisitorTourListService.readAllTravelVisitorTourListsByTravelId(travelId));
    }
}