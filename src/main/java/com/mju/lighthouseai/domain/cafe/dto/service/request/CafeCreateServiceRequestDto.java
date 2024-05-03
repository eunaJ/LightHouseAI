package com.mju.lighthouseai.domain.cafe.dto.service.request;

public record CafeCreateServiceRequestDto(
    String title,
    String location,
    Integer price,
    String menu,
    String opentime,
    String closetime,
    String constituency_name
) {

}
