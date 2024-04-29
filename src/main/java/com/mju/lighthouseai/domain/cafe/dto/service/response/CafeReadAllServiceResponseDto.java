package com.mju.lighthouseai.domain.cafe.dto.service.response;


import com.mju.lighthouseai.domain.user.entity.User;

public record CafeReadAllServiceResponseDto(
    String title,
    String location,
    int price,
    String menu,
    String opentime,
    String closetime,
    String user_name
) {
}
