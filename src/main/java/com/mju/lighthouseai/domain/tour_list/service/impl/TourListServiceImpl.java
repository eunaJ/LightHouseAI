package com.mju.lighthouseai.domain.tour_list.service.impl;

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

    public void createTourList(TourListCreateServiceRequestDto requestDto, User user){
        Constituency constituency = constituencyRepository.findByConstituency(requestDto.constituency_name()
            ).orElseThrow(()-> new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
        TourList tourList = tourListEntityMapper.toTourList(requestDto,user,constituency);
        tourListRepository.save(tourList);
    }

    @Transactional
    public void updateTourList(Long id, TourListUpdateServiceRequestDto requestDto){
        TourList tourList = findTourList(id);
        Constituency constituency = constituencyRepository.findByConstituency(requestDto.constituency_name()
        ).orElseThrow(()-> new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
        tourList.updateTourList(requestDto.title(), requestDto.location(), requestDto.price(),
             requestDto.opentime(), requestDto.closetime(),constituency);
    }

    private TourList findTourList(Long id){
        return tourListRepository.findById(id)
            .orElseThrow(()-> new NotFoundTourListException(TourListErrorCode.NOT_FOUND_TOURLIST));
    }

    public void deleteTourList(Long id) {
        TourList tourList = tourListRepository.findById(id)
            .orElseThrow(() -> new NotFoundTourListException(TourListErrorCode.NOT_FOUND_TOURLIST));
        tourListRepository.delete(tourList);
    }

    public List<TourListReadAllServiceResponseDto> readAllTourLists(){
        List<TourList> tourLists = tourListRepository.findAll();
        return tourListEntityMapper.toTourListReadAllResponseDto(tourLists);
    }
}
