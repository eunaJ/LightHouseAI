package com.mju.lighthouseai.global.naversearch;

import java.util.List;
import lombok.Builder;

@Builder
public record NaverSearchResponse(
    String lastBuildDate,
    int total,
    int start,
    int display,
    List<NaverSearchItem> items)
{
}
