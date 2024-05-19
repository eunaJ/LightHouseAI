package com.mju.lighthouseai.domain.travel.dto.service.request;

public record TravelUpdateServiceRequestDto(
    String title,
    Byte serving,
    Byte star,
    Boolean imageChange

) {
}