package com.mju.lighthouseai.domain.cafe.service.impl;

import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.cafe.exceoption.CafeErrorCode;
import com.mju.lighthouseai.domain.cafe.exceoption.NotFoundCafeException;
import com.mju.lighthouseai.domain.cafe.mapper.service.CafeEntityMapper;
import com.mju.lighthouseai.domain.cafe.repository.CafeRepository;
import com.mju.lighthouseai.domain.cafe.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CafeServiceImpl implements CafeService {

    private final CafeRepository cafeRepository;
    private final CafeEntityMapper cafeEntityMapper;

    public void createCafe(CafeCreateServiceRequestDto requestDto){
        Cafe cafe = cafeEntityMapper.tocafe(requestDto);
        cafeRepository.save(cafe);
    }

    @Transactional
    public void updateCafe(Long id,CafeUpdateServiceRequestDto requestDto){
        Cafe cafe = findCafe(id);
        cafe.updateCafe(requestDto.title(), requestDto.location(), requestDto.price(),
            requestDto.menu(), requestDto.opentime(), requestDto.closetime());
    }
    private Cafe findCafe(Long id){
        return cafeRepository.findById(id)
            .orElseThrow(()-> new NotFoundCafeException(CafeErrorCode.NOT_FOUND_CAFE));
    }
    public void deleteCafe(Long id) {
        Cafe food = cafeRepository.findById(id)
            .orElseThrow(() -> new NotFoundCafeException(CafeErrorCode.NOT_FOUND_CAFE));
        cafeRepository.delete(food);
    }

}
