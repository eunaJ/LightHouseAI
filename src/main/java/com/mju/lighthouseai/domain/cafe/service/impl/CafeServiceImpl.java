package com.mju.lighthouseai.domain.cafe.service.impl;

import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.response.CafeReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.cafe.exception.CafeErrorCode;
import com.mju.lighthouseai.domain.cafe.exception.NotFoundCafeException;
import com.mju.lighthouseai.domain.cafe.mapper.service.CafeEntityMapper;
import com.mju.lighthouseai.domain.cafe.repository.CafeRepository;
import com.mju.lighthouseai.domain.cafe.service.CafeService;
import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.constituency.exception.ConstituencyErrorCode;
import com.mju.lighthouseai.domain.constituency.exception.NotFoundConstituencyException;
import com.mju.lighthouseai.domain.constituency.repository.ConstituencyRepository;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.domain.user.entity.UserRole;
import com.mju.lighthouseai.domain.user.exception.NotFoundUserException;
import com.mju.lighthouseai.domain.user.exception.UserErrorCode;
import com.mju.lighthouseai.global.naversearch.NaverSearchItem;
import com.mju.lighthouseai.global.naversearch.NaverSearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CafeServiceImpl implements CafeService {

    private final CafeRepository cafeRepository;
    private final CafeEntityMapper cafeEntityMapper;
    private final ConstituencyRepository constituencyRepository;
    private final NaverSearchService naverSearchService;

    public void createCafe(CafeCreateServiceRequestDto requestDto,User user){
        Constituency constituency =constituencyRepository.findByConstituency(requestDto.constituency_name())
            .orElseThrow(()->new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
        List<NaverSearchItem> naverSearchItems = naverSearchService.searchLocal(constituency.getConstituency()+requestDto.title());
        if (naverSearchItems.get(Math.toIntExact(requestDto.item_id())).getAddress().matches("(.*)"+constituency.getConstituency()+"(.*)")&&
        naverSearchItems.get(Math.toIntExact(requestDto.item_id())).getCategory().matches("(.*)카페(.*)")) {
            String title = naverSearchItems.get(Math.toIntExact(requestDto.item_id())).getTitle()
                .replace("<b>", "");
            title = title.replace("</b>","");
            String location = naverSearchItems.get(Math.toIntExact(requestDto.item_id())).getAddress();
            Cafe cafe = Cafe.builder()
                .title(title)
                .constituency(constituency)
                .location(location)
                .closetime(requestDto.closetime())
                .opentime(requestDto.opentime())
                .user(user)
                .build();
            cafeRepository.save(cafe);
        }else{
            Cafe cafe = cafeEntityMapper.tocafe(requestDto,user,constituency);
            cafeRepository.save(cafe);
        }
    }

    @Transactional
    public void updateCafe(Long id,CafeUpdateServiceRequestDto requestDto,User user){
        checkUserRole(user);
        Cafe cafe = findCafe(id);
        cafe.updateCafe(requestDto.title(), requestDto.location(),  requestDto.opentime(), requestDto.closetime());
    }
    private Cafe findCafe(Long id){
        return cafeRepository.findById(id)
            .orElseThrow(()-> new NotFoundCafeException(CafeErrorCode.NOT_FOUND_CAFE));
    }
    public void deleteCafe(Long id,User user) {
        checkUserRole(user);
        Cafe food = cafeRepository.findById(id)
            .orElseThrow(() -> new NotFoundCafeException(CafeErrorCode.NOT_FOUND_CAFE));
        cafeRepository.delete(food);
    }
    public List<CafeReadAllServiceResponseDto> readAllCafes(){
        List<Cafe> cafes = cafeRepository.findAll();
        return cafeEntityMapper.toCafeReadAllResponseDto(cafes);
    }
    public CafeReadAllServiceResponseDto readCafe(Long id){
        Cafe cafe = cafeRepository.findById(id).
            orElseThrow(()->new NotFoundCafeException(CafeErrorCode.NOT_FOUND_CAFE));
        return cafeEntityMapper.toCafeReadResponseDto(cafe);
    }
    public List<CafeReadAllServiceResponseDto> readConstituencyCafe(Long id){
        Constituency constituency = constituencyRepository.findById(id).
            orElseThrow(()->new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
        List<Cafe> cafes = cafeRepository.findByConstituencyId(constituency.getId());
        return cafeEntityMapper.toCafeReadAllResponseDto(cafes);
    }
    private void checkUserRole(User user) {
        if (!(user.getRole().equals(UserRole.ADMIN))) {
            throw new NotFoundUserException(UserErrorCode.NOT_ADMIN);
        }
    }
}
