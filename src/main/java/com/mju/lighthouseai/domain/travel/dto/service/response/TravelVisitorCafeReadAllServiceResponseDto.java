package com.mju.lighthouseai.domain.travel.dto.service.response;

public record TravelVisitorCafeReadAllServiceResponseDto (
    String title,
    Long travel_expense,
    Byte serving,
    Byte star
){
}
