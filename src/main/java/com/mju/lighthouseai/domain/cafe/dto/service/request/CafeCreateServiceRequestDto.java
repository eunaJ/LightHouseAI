package com.mju.lighthouseai.domain.cafe.dto.service.request;

import lombok.Getter;

public record CafeCreateServiceRequestDto(
    String title,
    String location,
    String opentime,
    String closetime,
    String constituency_name,
    Long item_id
) {

}
