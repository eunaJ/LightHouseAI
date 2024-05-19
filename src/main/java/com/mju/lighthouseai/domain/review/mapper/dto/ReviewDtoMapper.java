package com.mju.lighthouseai.domain.review.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


import com.mju.lighthouseai.domain.review.dto.controller.ReviewCreateControllerRequestDto;
import com.mju.lighthouseai.domain.review.dto.controller.ReviewUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.review.dto.service.request.ReviewCreateServiceRequestDto;
import com.mju.lighthouseai.domain.review.dto.service.request.ReviewUpdateServiceRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface ReviewDtoMapper {
    ReviewCreateServiceRequestDto toReviewCreateServiceDto(
        ReviewCreateControllerRequestDto controllerRequestDto
    );

    ReviewUpdateServiceRequestDto toReviewUpdateServiceDto(
        ReviewUpdateControllerRequestDto controllerRequestDto
    );
}


