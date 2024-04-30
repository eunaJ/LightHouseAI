package com.mju.lighthouseai.domain.shoppingmall.dto.service;

public record ShoppingMallCreateServiceRequestDto(
    String title,
    String location,
    String opentime,
    String closetime,
    String constituency_name

) {

}
