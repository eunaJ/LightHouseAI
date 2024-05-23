package com.mju.lighthouseai.domain.travel.dto.controller;

public record TravelUpdateControllerRequestDto(
    String title,
    Byte serving,
    Byte star,
    Boolean imageChange
) {
}