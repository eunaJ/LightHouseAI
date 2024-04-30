package com.mju.lighthouseai.domain.shoppingmall.dto.controller;

public record ShoppingMallCreateControllerRequestDto(
    String title,
    String location,
    String opentime,
    String closetime,
    String constituency_name
) {

}
