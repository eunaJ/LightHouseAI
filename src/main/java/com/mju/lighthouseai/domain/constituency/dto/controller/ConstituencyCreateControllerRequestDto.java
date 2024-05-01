package com.mju.lighthouseai.domain.constituency.dto.controller;

import com.mju.lighthouseai.domain.region.entity.Region;

public record ConstituencyCreateControllerRequestDto(
        String constituency,
        Region region
) {
}
