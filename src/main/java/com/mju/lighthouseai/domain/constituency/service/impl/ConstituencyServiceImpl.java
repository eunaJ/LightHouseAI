package com.mju.lighthouseai.domain.constituency.service.impl;

import com.mju.lighthouseai.domain.constituency.dto.service.request.ConstituencyCreateServiceRequestDto;
import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.constituency.mapper.service.ConstituencyEntityMapper;
import com.mju.lighthouseai.domain.constituency.repository.ConstituencyRepository;
import com.mju.lighthouseai.domain.constituency.service.ConstituencyService;
import com.mju.lighthouseai.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConstituencyServiceImpl implements ConstituencyService {
    private final ConstituencyEntityMapper constituencyEntityMapper;
    private final ConstituencyRepository constituencyRepository;

    public void createConstituency(ConstituencyCreateServiceRequestDto requestDto, User user){
        Constituency constituency = constituencyEntityMapper.toConstituency(requestDto, user);
        constituencyRepository.save(constituency);
    }
}
