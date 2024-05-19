package com.mju.lighthouseai.domain.travel.dto.service.request;

public record TravelUpdateServiceRequestDto(
    String title,
    Integer travel_expense,
    Byte serving,
    Byte star,
    Boolean imageChange

) {
}