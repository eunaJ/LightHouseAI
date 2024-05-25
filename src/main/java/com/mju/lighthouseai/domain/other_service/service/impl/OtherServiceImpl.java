package com.mju.lighthouseai.domain.other_service.service.impl;

import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.constituency.exception.ConstituencyErrorCode;
import com.mju.lighthouseai.domain.constituency.exception.NotFoundConstituencyException;
import com.mju.lighthouseai.domain.constituency.repository.ConstituencyRepository;
import com.mju.lighthouseai.domain.other_service.dto.service.request.OtherServiceCreateServiceRequestDto;
import com.mju.lighthouseai.domain.other_service.dto.service.response.OtherServiceReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.other_service.dto.service.request.OtherServiceUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.other_service.entity.OtherServiceEntity;
import com.mju.lighthouseai.domain.other_service.exception.NotFoundOtherServiceException;
import com.mju.lighthouseai.domain.other_service.mapper.service.OtherServiceEntityMapper;
import com.mju.lighthouseai.domain.other_service.repository.OtherServiceRepository;
import com.mju.lighthouseai.domain.other_service.service.OtherService;
import com.mju.lighthouseai.domain.other_service.exception.OtherServiceErrorCode;
import com.mju.lighthouseai.domain.restaurant.entity.Restaurant;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.domain.user.entity.UserRole;
import com.mju.lighthouseai.domain.user.exception.NotFoundUserException;
import com.mju.lighthouseai.domain.user.exception.UserErrorCode;
import com.mju.lighthouseai.global.naversearch.NaverSearchItem;
import com.mju.lighthouseai.global.naversearch.NaverSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OtherServiceImpl implements OtherService {

    private final OtherServiceRepository otherServiceRepository;
    private final OtherServiceEntityMapper otherServiceEntityMapper;
    private final ConstituencyRepository constituencyRepository;
    private final NaverSearchService naverSearchService;

    public void createOtherService(OtherServiceCreateServiceRequestDto requestDto, User user){
        Constituency constituency = constituencyRepository.findByConstituency(requestDto.constituency_name()
        ).orElseThrow(()-> new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
        List<NaverSearchItem> naverSearchItems = naverSearchService.searchLocal(constituency.getConstituency()+requestDto.title());
        if (naverSearchItems.get(Math.toIntExact(requestDto.item_id())).getAddress()
            .matches("(.*)"+constituency.getConstituency()+"(.*)")){
            String title = naverSearchItems.get(Math.toIntExact(requestDto.item_id())).getTitle()
                .replace("<b>", "");
            title = title.replace("</b>","");
            String location = naverSearchItems.get(Math.toIntExact(requestDto.item_id())).getAddress();
            OtherServiceEntity otherServiceEntity = OtherServiceEntity.builder()
                .title(title)
                .constituency(constituency)
                .location(location)
                .closetime(requestDto.closetime())
                .opentime(requestDto.opentime())
                .user(user)
                .build();
            otherServiceRepository.save(otherServiceEntity);
        } else {
            OtherServiceEntity otherServiceEntity = otherServiceEntityMapper.toOtherService(
                requestDto, user, constituency);
            otherServiceRepository.save(otherServiceEntity);
        }
    }

    @Transactional
    public void updateOtherService(Long id, OtherServiceUpdateServiceRequestDto requestDto, User user){
        checkUserRole(user);
        OtherServiceEntity otherServiceEntity = findOtherService(id);
        Constituency constituency = constituencyRepository.findByConstituency(requestDto.constituency_name()
        ).orElseThrow(()-> new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
        otherServiceEntity.updateOtherService(requestDto.title(), requestDto.location(),
                requestDto.opentime(), requestDto.closetime(),constituency);
    }

    public void deleteOtherService(Long id,User user) {
        checkUserRole(user);
        OtherServiceEntity otherServiceEntity = otherServiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundOtherServiceException(OtherServiceErrorCode.NOT_FOUND_OtherService));
        otherServiceRepository.delete(otherServiceEntity);
    }

    public List<OtherServiceReadAllServiceResponseDto> readAllOtherServices(){
        List<OtherServiceEntity> otherServices = otherServiceRepository.findAll();
        return otherServiceEntityMapper.toOtherServiceReadAllResponseDto(otherServices);
    }

    public List<OtherServiceReadAllServiceResponseDto> readConstituencyOtherServices(Long id){
        Constituency constituency =constituencyRepository.findById(id)
            .orElseThrow(()->new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
        List<OtherServiceEntity> otherServices = otherServiceRepository.findByConstituencyId(constituency.getId());
        return otherServiceEntityMapper.toOtherServiceReadAllResponseDto(otherServices);
    }

    public OtherServiceReadAllServiceResponseDto readOtherService(Long id){
        OtherServiceEntity otherServiceEntity = otherServiceRepository.findById(id)
                .orElseThrow(()->new NotFoundOtherServiceException(OtherServiceErrorCode.NOT_FOUND_OtherService));
        return otherServiceEntityMapper.toOtherServiceReadResponseDto(otherServiceEntity);
    }

    private OtherServiceEntity findOtherService(Long id){
        return otherServiceRepository.findById(id)
                .orElseThrow(()-> new NotFoundOtherServiceException(OtherServiceErrorCode.NOT_FOUND_OtherService));
    }
    private void checkUserRole(User user) {
        if (!(user.getRole().equals(UserRole.ADMIN))) {
            throw new NotFoundUserException(UserErrorCode.NOT_ADMIN);
        }
    }
}
