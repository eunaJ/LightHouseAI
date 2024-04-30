package com.mju.lighthouseai.domain.other_service.service.impl;

import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.constituency.exception.ConstituencyErrorCode;
import com.mju.lighthouseai.domain.constituency.exception.NotFoundConstituencyException;
import com.mju.lighthouseai.domain.constituency.repository.ConstituencyRepository;
import com.mju.lighthouseai.domain.other_service.dto.service.OtherServiceCreateServiceRequestDto;
import com.mju.lighthouseai.domain.other_service.entity.OtherServiceEntity;
import com.mju.lighthouseai.domain.other_service.mapper.service.OtherServiceEntityMapper;
import com.mju.lighthouseai.domain.other_service.repository.OtherServiceRepository;
import com.mju.lighthouseai.domain.other_service.service.OtherService;
import com.mju.lighthouseai.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OtherServiceImpl implements OtherService {

    private final OtherServiceRepository otherServiceRepository;
    private final OtherServiceEntityMapper otherServiceEntityMapper;
    private final ConstituencyRepository constituencyRepository;

    public void createOtherService(OtherServiceCreateServiceRequestDto requestDto, User user){
        Constituency constituency = constituencyRepository.findByConstituency(requestDto.constituency_name())
            .orElseThrow(()->new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
        OtherServiceEntity otherService = otherServiceEntityMapper.toOtherService(requestDto,user,constituency);
        otherServiceRepository.save(otherService);
    }

}
