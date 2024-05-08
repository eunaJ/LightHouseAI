package com.mju.lighthouseai.domain.shoppingmall.dto.controller;

public record ShoppingMallUpdateControllerRequestDto(
    String title,
    String location,
    String opentime,
    String closetime,
    String constituency_name
) {

}
