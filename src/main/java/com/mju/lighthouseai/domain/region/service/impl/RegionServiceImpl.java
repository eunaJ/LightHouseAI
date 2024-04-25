package com.mju.lighthouseai.domain.region.service.impl;

import com.mju.lighthouseai.domain.region.dto.service.request.RegionCreateServiceRequestDto;
import com.mju.lighthouseai.domain.region.entity.Region;
import com.mju.lighthouseai.domain.region.mapper.service.RegionEntityMapper;
import com.mju.lighthouseai.domain.region.repository.RegionRepository;
import com.mju.lighthouseai.domain.region.service.RegionService;
import com.mju.lighthouseai.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;
    private final RegionEntityMapper regionEntityMapper;

    public void createRegion(RegionCreateServiceRequestDto requestDto, User user){
        Region region = regionEntityMapper.toRegion(requestDto, user);
        regionRepository.save(region);
    }
}