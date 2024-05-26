package com.mju.lighthouseai.domain.other_service.dto.service.request;

public record OtherServiceCreateServiceRequestDto(
    String title,
    String location,
    String opentime,
    String closetime,
    String constituency_name,
    Long item_id

) {

}
