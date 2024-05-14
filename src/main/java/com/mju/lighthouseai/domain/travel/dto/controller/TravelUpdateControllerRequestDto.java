package com.mju.lighthouseai.domain.travel.dto.controller;

public record TravelUpdateControllerRequestDto(
    String title,
    Long travel_expense,
    Byte serving,
    Byte star,
    String constituency,
    Boolean imageChange
) {
}