package com.mju.lighthouseai.domain.tour_list.dto.controller;

import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.user.entity.User;

public record TourListCreateControllerRequestDto(
    String title,
    String location,
    String opentime,
    String closetime,
    String constituency_name,
    Long item_id
) {

}
