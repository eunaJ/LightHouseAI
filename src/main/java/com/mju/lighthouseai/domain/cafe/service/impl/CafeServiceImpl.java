package com.mju.lighthouseai.domain.cafe.service.impl;

import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.cafe.mapper.service.CafeEntityMapper;
import com.mju.lighthouseai.domain.cafe.repository.CafeRepository;
import com.mju.lighthouseai.domain.cafe.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CafeServiceImpl implements CafeService {

    private final CafeRepository cafeRepository;
    private final CafeEntityMapper cafeEntityMapper;

    public void createCafe(CafeCreateServiceRequestDto requestDto){
        Cafe cafe = cafeEntityMapper.tocafe(requestDto);
        cafeRepository.save(cafe);
    }

}
