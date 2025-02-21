package com.mju.lighthouseai.domain.tour_list.dto.service.request;

import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.user.entity.User;

public record TourListCreateServiceRequestDto(
    String title,
    String location,
    String opentime,
    String closetime,
    String constituency_name,
    Long item_id
) {

}
