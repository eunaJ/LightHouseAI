package com.mju.lighthouseai.domain.shoppingmall.dto.service.request;

public record ShoppingMallUpdateServiceRequestDto(
    String title,
    String location,
    String opentime,
    String closetime,
    String constituency_name

) {

}
