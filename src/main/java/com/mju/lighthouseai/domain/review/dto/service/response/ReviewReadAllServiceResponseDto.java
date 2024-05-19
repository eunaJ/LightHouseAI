package com.mju.lighthouseai.domain.review.dto.service.response;


public record ReviewReadAllServiceResponseDto(

    Long id,
    String content,
    String writer
) {
}
