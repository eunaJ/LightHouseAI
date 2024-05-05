package com.mju.lighthouseai.domain.travel_visitor_cafe.dto.controller;

import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.user.entity.User;

public record TravelVisitorCafeCreateControllerRequestDto(
        int cost,
        String opentime,
        String closetime,
        String location,
        String cafe_title
) {
}
