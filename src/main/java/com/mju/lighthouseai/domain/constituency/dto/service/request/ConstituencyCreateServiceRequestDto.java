package com.mju.lighthouseai.domain.constituency.dto.service.request;

import com.mju.lighthouseai.domain.region.entity.Region;

public record ConstituencyCreateServiceRequestDto(
        String constituency,
        Region region
) {
}
