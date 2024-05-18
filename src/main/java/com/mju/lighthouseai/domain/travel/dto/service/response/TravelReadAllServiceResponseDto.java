package com.mju.lighthouseai.domain.travel.dto.service.response;

import lombok.Builder;

@Builder
public record TravelReadAllServiceResponseDto(
    Long id,
    String constituency_name,
    String title,
    Integer travel_expense,
    Byte serving,
    Byte star,
    String image_url,
    String writer

){
}
