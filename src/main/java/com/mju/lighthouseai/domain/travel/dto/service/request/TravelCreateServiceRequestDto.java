package com.mju.lighthouseai.domain.travel.dto.service.request;

public record TravelCreateServiceRequestDto(
    String title,
    Byte serving,
    Byte star,
    String constituency

) {
}
