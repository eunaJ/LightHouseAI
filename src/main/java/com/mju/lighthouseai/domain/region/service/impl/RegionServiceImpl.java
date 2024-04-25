package com.mju.lighthouseai.domain.region.service.impl;

import com.mju.lighthouseai.domain.region.dto.service.request.RegionCreateServiceRequestDto;
import com.mju.lighthouseai.domain.region.dto.service.request.RegionUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.region.entity.Region;
import com.mju.lighthouseai.domain.region.exception.NotFoundRegionException;
import com.mju.lighthouseai.domain.region.exception.RegionErrorCode;
import com.mju.lighthouseai.domain.region.mapper.service.RegionEntityMapper;
import com.mju.lighthouseai.domain.region.repository.RegionRepository;
import com.mju.lighthouseai.domain.region.service.RegionService;
import com.mju.lighthouseai.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;
    private final RegionEntityMapper regionEntityMapper;

    public void createRegion(RegionCreateServiceRequestDto requestDto, User user){
        Region region = regionEntityMapper.toRegion(requestDto, user);
        regionRepository.save(region);
    }

    @Transactional
    public void updateRegion(Long id, RegionUpdateServiceRequestDto requestDto){
        Region region = findRegion(id);
        region.updateRegion(requestDto.region_name());
    }

    private Region findRegion(Long id){
        return regionRepository.findById(id)
                .orElseThrow(()-> new NotFoundRegionException(RegionErrorCode.NOT_FOUND_REGION));
    }

    public void deleteRegion(Long id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new NotFoundRegionException(RegionErrorCode.NOT_FOUND_REGION));
        regionRepository.delete(region);
    }
}