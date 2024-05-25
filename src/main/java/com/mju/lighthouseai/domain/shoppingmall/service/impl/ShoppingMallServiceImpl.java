package com.mju.lighthouseai.domain.shoppingmall.service.impl;

import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.constituency.exception.ConstituencyErrorCode;
import com.mju.lighthouseai.domain.constituency.exception.NotFoundConstituencyException;
import com.mju.lighthouseai.domain.constituency.repository.ConstituencyRepository;
import com.mju.lighthouseai.domain.shoppingmall.dto.service.request.ShoppingMallCreateServiceRequestDto;
import com.mju.lighthouseai.domain.shoppingmall.dto.service.request.ShoppingMallUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.shoppingmall.dto.service.response.ShoppingMallReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.shoppingmall.entity.ShoppingMall;
import com.mju.lighthouseai.domain.shoppingmall.exception.NotFoundShoppingMallException;
import com.mju.lighthouseai.domain.shoppingmall.exception.ShoppingMallErrorCode;
import com.mju.lighthouseai.domain.shoppingmall.mapper.service.ShoppingMallEntityMapper;
import com.mju.lighthouseai.domain.shoppingmall.repository.ShoppingMallRepository;
import com.mju.lighthouseai.domain.shoppingmall.service.ShoppingMallService;
import com.mju.lighthouseai.domain.tour_list.entity.TourList;
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
public class ShoppingMallServiceImpl implements ShoppingMallService {

    private final ShoppingMallRepository shoppingMallRepository;
    private final ShoppingMallEntityMapper shoppingMallEntityMapper;
    private final ConstituencyRepository constituencyRepository;
    private final NaverSearchService naverSearchService;

    public void createShoppingMall(ShoppingMallCreateServiceRequestDto requestDto, User user){
        Constituency constituency = constituencyRepository.findByConstituency(requestDto.constituency_name())
            .orElseThrow(()->new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
        List<NaverSearchItem> naverSearchItems = naverSearchService.searchLocal(constituency.getConstituency()+requestDto.title());
        if (naverSearchItems.get(Math.toIntExact(requestDto.item_id())).getAddress()
            .matches("(.*)"+constituency.getConstituency()+"(.*)")){
            String title = naverSearchItems.get(Math.toIntExact(requestDto.item_id())).getTitle()
                .replace("<b>", "");
            title = title.replace("</b>","");
            String location = naverSearchItems.get(Math.toIntExact(requestDto.item_id())).getAddress();
            ShoppingMall shoppingMall = ShoppingMall.builder()
                .title(title)
                .constituency(constituency)
                .location(location)
                .closetime(requestDto.closetime())
                .opentime(requestDto.opentime())
                .user(user)
                .build();
            shoppingMallRepository.save(shoppingMall);
        } else {
            ShoppingMall shoppingMall = shoppingMallEntityMapper.toShoppingMall(requestDto, user,
                constituency);
            shoppingMallRepository.save(shoppingMall);
        }
    }
    @Transactional
    public void updateShoppingMall(Long id, ShoppingMallUpdateServiceRequestDto requestDto,User user){
        checkUserRole(user);
        ShoppingMall shoppingMall = findShoppingMall(id);
        Constituency constituency = constituencyRepository.findByConstituency(requestDto.constituency_name()
        ).orElseThrow(()-> new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
        shoppingMall.updateShoppingMall(requestDto.title(), requestDto.location(),
            requestDto.opentime(), requestDto.closetime(),constituency);
    }
    public ShoppingMallReadAllServiceResponseDto readShoppingMall(Long id){
        ShoppingMall shoppingMall = findShoppingMall(id);
        return shoppingMallEntityMapper.toShoppingMallReadResponseDto(shoppingMall);
    }
    public List<ShoppingMallReadAllServiceResponseDto> readAllShoppingMall(){
        List<ShoppingMall> shoppingMall= shoppingMallRepository.findAll();
        return shoppingMallEntityMapper.toShoppingMallReadAllResponseDto(shoppingMall);
    }
    public List<ShoppingMallReadAllServiceResponseDto> readConstituencyShoppingMall(Long id){
        Constituency constituency = constituencyRepository.findById(id)
            .orElseThrow(()->new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
        List<ShoppingMall> shoppingMall= shoppingMallRepository.findByConstituencyId(constituency.getId());
        return shoppingMallEntityMapper.toShoppingMallReadAllResponseDto(shoppingMall);
    }

    private ShoppingMall findShoppingMall(Long id){
        return shoppingMallRepository.findById(id)
            .orElseThrow(()-> new NotFoundShoppingMallException(ShoppingMallErrorCode.NOT_FOUND_ShoppingMall));
    }
    public List<ShoppingMallReadAllServiceResponseDto> readConstituencyShoppingMalls(Long id){
        List<ShoppingMall> shoppingMalls = shoppingMallRepository.findByConstituencyId(id);
        return shoppingMallEntityMapper.toShoppingMallReadAllResponseDto(shoppingMalls);
    }
    private void checkUserRole(User user) {
        if (!(user.getRole().equals(UserRole.ADMIN))) {
            throw new NotFoundUserException(UserErrorCode.NOT_ADMIN);
        }
    }
}
