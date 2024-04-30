package com.mju.lighthouseai.domain.tour_list.dto.service;

import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.user.entity.User;

public record TourListCreateServiceRequestDto(
    String title,
    String location,
    int price,
    String opentime,
    String closetime,
    String constituency_name
) {

}
