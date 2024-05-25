package com.mju.lighthouseai.domain.tour_list.service.impl;

import com.mju.lighthouseai.domain.cafe.dto.service.response.CafeReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.constituency.exception.ConstituencyErrorCode;
import com.mju.lighthouseai.domain.constituency.exception.NotFoundConstituencyException;
import com.mju.lighthouseai.domain.constituency.repository.ConstituencyRepository;
import com.mju.lighthouseai.domain.tour_list.dto.service.request.TourListCreateServiceRequestDto;
import com.mju.lighthouseai.domain.tour_list.dto.service.request.TourListUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.tour_list.dto.service.response.TourListReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.tour_list.entity.TourList;
import com.mju.lighthouseai.domain.tour_list.exceoption.NotFoundTourListException;
import com.mju.lighthouseai.domain.tour_list.exceoption.TourListErrorCode;
import com.mju.lighthouseai.domain.tour_list.mapper.service.TourListEntityMapper;
import com.mju.lighthouseai.domain.tour_list.repository.TourListRepository;
import com.mju.lighthouseai.domain.tour_list.service.TourListService;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.domain.user.entity.UserRole;
import com.mju.lighthouseai.domain.user.exception.NotFoundUserException;
import com.mju.lighthouseai.domain.user.exception.UserErrorCode;
import com.mju.lighthouseai.domain.user.repository.UserRepository;
import com.mju.lighthouseai.global.naversearch.NaverSearchItem;
import com.mju.lighthouseai.global.naversearch.NaverSearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TourListServiceImpl implements TourListService {

    private final TourListRepository tourListRepository;
    private final TourListEntityMapper tourListEntityMapper;
    private final ConstituencyRepository constituencyRepository;
    private final NaverSearchService naverSearchService;



    public void createTourList(TourListCreateServiceRequestDto requestDto, User user){
        Constituency constituency = constituencyRepository.findByConstituency(requestDto.constituency_name()
            ).orElseThrow(()-> new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
        List<NaverSearchItem> naverSearchItems = naverSearchService.searchLocal(constituency.getConstituency()+requestDto.title());
        if (naverSearchItems.get(Math.toIntExact(requestDto.item_id())).getAddress()
            .matches("(.*)"+constituency.getConstituency()+"(.*)")){
            String title = naverSearchItems.get(Math.toIntExact(requestDto.item_id())).getTitle()
                .replace("<b>", "");
            title = title.replace("</b>","");
            String location = naverSearchItems.get(Math.toIntExact(requestDto.item_id())).getAddress();
            TourList tourList = TourList.builder()
                .title(title)
                .constituency(constituency)
                .location(location)
                .closetime(requestDto.closetime())
                .opentime(requestDto.opentime())
                .user(user)
                .build();
            tourListRepository.save(tourList);
        } else{
                TourList tourList = tourListEntityMapper.toTourList(requestDto,user,constituency);
                tourListRepository.save(tourList);
            }
    }

    @Transactional
    public void updateTourList(Long id, TourListUpdateServiceRequestDto requestDto,User user){
        checkUserRole(user);
        TourList tourList = findTourList(id);
        Constituency constituency = constituencyRepository.findByConstituency(requestDto.constituency_name()
        ).orElseThrow(()-> new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
        tourList.updateTourList(requestDto.title(), requestDto.location(),
             requestDto.opentime(), requestDto.closetime(),constituency);
    }

    public void deleteTourList(Long id,User user) {
        checkUserRole(user);
        TourList tourList = tourListRepository.findById(id)
            .orElseThrow(() -> new NotFoundTourListException(TourListErrorCode.NOT_FOUND_TOURLIST));
        tourListRepository.delete(tourList);
    }

    public List<TourListReadAllServiceResponseDto> readAllTourLists(){
        List<TourList> tourLists = tourListRepository.findAll();
        return tourListEntityMapper.toTourListReadAllResponseDto(tourLists);

    }
    public TourListReadAllServiceResponseDto readTourList(Long id){
        TourList tourList =tourListRepository.findById(id)
            .orElseThrow(()->new NotFoundTourListException(TourListErrorCode.NOT_FOUND_TOURLIST));
        return tourListEntityMapper.toTourListReadResponseDto(tourList);
    }

    private TourList findTourList(Long id){
        return tourListRepository.findById(id)
            .orElseThrow(()-> new NotFoundTourListException(TourListErrorCode.NOT_FOUND_TOURLIST));
    }
    public List<TourListReadAllServiceResponseDto> readConstituencyTourLists(Long id){
        Constituency constituency = constituencyRepository.findById(id).
            orElseThrow(()->new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
        List<TourList> tourLists = tourListRepository.findByConstituencyId(constituency.getId());
        return tourListEntityMapper.toTourListReadAllResponseDto(tourLists);
    }
    private void checkUserRole(User user) {
        if (!(user.getRole().equals(UserRole.ADMIN))) {
            throw new NotFoundUserException(UserErrorCode.NOT_ADMIN);
        }
    }
}
