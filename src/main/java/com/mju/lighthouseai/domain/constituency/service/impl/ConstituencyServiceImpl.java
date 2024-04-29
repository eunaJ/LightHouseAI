package com.mju.lighthouseai.domain.constituency.service.impl;

import com.mju.lighthouseai.domain.constituency.dto.service.request.ConstituencyCreateServiceRequestDto;
import com.mju.lighthouseai.domain.constituency.dto.service.request.ConstituencyUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.constituency.exception.ConstituencyErrorCode;
import com.mju.lighthouseai.domain.constituency.exception.NotFoundConstituencyException;
import com.mju.lighthouseai.domain.constituency.mapper.service.ConstituencyEntityMapper;
import com.mju.lighthouseai.domain.constituency.repository.ConstituencyRepository;
import com.mju.lighthouseai.domain.constituency.service.ConstituencyService;
import com.mju.lighthouseai.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ConstituencyServiceImpl implements ConstituencyService {
    private final ConstituencyEntityMapper constituencyEntityMapper;
    private final ConstituencyRepository constituencyRepository;

    public void createConstituency(ConstituencyCreateServiceRequestDto requestDto, User user){
        Constituency constituency = constituencyEntityMapper.toConstituency(requestDto, user);
        constituencyRepository.save(constituency);
    }

    @Transactional
    public void updateConstituency(Long id, ConstituencyUpdateServiceRequestDto requestDto){
        Constituency constituency = findConstituency(id);
        constituency.updateConstituency(requestDto.constituency());
    }

    private Constituency findConstituency(Long id){
        return constituencyRepository.findById(id)
                .orElseThrow(()-> new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
    }

    public void deleteConstituency(Long id) {
        Constituency constituency = constituencyRepository.findById(id)
                .orElseThrow(() -> new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
        constituencyRepository.delete(constituency);
    }
}
