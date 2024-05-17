package com.mju.lighthouseai.domain.travel.dto.controller;


import io.lettuce.core.StrAlgoArgs.By;

public record TravelCreateControllerRequestDto(
        String title,
        Byte serving,
        Byte star,
        String constituency

) {

}
