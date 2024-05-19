package com.mju.lighthouseai.domain.review.controller;

import com.mju.lighthouseai.domain.review.dto.controller.ReviewCreateControllerRequestDto;
import com.mju.lighthouseai.domain.review.dto.controller.ReviewUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.review.dto.service.request.ReviewCreateServiceRequestDto;
import com.mju.lighthouseai.domain.review.dto.service.request.ReviewUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.review.dto.service.response.ReviewReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.review.mapper.dto.ReviewDtoMapper;
import com.mju.lighthouseai.domain.review.service.ReviewService;
import com.mju.lighthouseai.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class ReviewController {
    private final ReviewDtoMapper reviewDtoMapper;
    private final ReviewService reviewService;

    @PostMapping("/boards/{boardId}/reviews/create")
    public ResponseEntity<?> createReview(
        @PathVariable Long boardId,
        @RequestBody ReviewCreateControllerRequestDto controllerRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        ReviewCreateServiceRequestDto serviceRequestDto =
            reviewDtoMapper.toReviewCreateServiceDto(controllerRequestDto);
        reviewService.createReview(boardId,serviceRequestDto,userDetails.user());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<?> updateReview(
        @PathVariable Long reviewId,
        @RequestBody ReviewUpdateControllerRequestDto controllerRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        ReviewUpdateServiceRequestDto serviceRequestDto =
            reviewDtoMapper.toReviewUpdateServiceDto(controllerRequestDto);
        reviewService.updateReview(serviceRequestDto,reviewId,userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<?> deleteCafe(
        @PathVariable Long reviewId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        reviewService.deleteReview(reviewId,userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/boards/{boardId}/reviews")
    public ResponseEntity<List<ReviewReadAllServiceResponseDto>> readAllReviews(
        @RequestParam(name = "page",defaultValue = "0") Integer page,
        @PathVariable (name = "boardId")Long boardId
    ){
       List<ReviewReadAllServiceResponseDto> reviews = reviewService.readReviews(boardId,page);
       return new ResponseEntity<>(reviews,HttpStatus.OK);
    }
 }
