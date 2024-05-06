package com.mju.lighthouseai.domain.travel_visitor_tour_list.service.impl;

import com.mju.lighthouseai.domain.tour_list.entity.TourList;
import com.mju.lighthouseai.domain.tour_list.exceoption.NotFoundTourListException;
import com.mju.lighthouseai.domain.tour_list.exceoption.TourListErrorCode;
import com.mju.lighthouseai.domain.tour_list.repository.TourListRepository;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.service.request.TravelVisitorTourListCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.entity.TravelVisitorTourList;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.mapper.service.TravelVisitorTourListEntityMapper;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.repository.TravelVisitorTourListRepository;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.service.TravelVisitorTourListService;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.global.s3.S3Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class TravelVisitorTourListServiceImpl implements TravelVisitorTourListService {
    private final TravelVisitorTourListRepository travelVisitorTourListRepository;
    private final TravelVisitorTourListEntityMapper travelVisitorTourListEntityMapper;
    private final TourListRepository tourListRepository;
    private final S3Provider s3Provider;

    private final String SEPARATOR = "/";
    private final String url = "https://light-house-ai.s3.ap-northeast-2.amazonaws.com/";
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public void createTravelVisitorTourList(TravelVisitorTourListCreateServiceRequestDto requestDto,
                                        User user,
                                        MultipartFile multipartFile
    ) throws IOException {
        String fileName;
        String fileUrl;
        TourList tourList = tourListRepository.findTourListByTitle(requestDto.tourList_title())
                .orElseThrow(()->new NotFoundTourListException(TourListErrorCode.NOT_FOUND_TOURLIST));
        if (multipartFile == null || multipartFile.isEmpty()){
            fileUrl = null;
            TravelVisitorTourList travelVisitorTourList =
                    travelVisitorTourListEntityMapper.toTravelVisitorTourList(requestDto,user,tourList,fileUrl);
            travelVisitorTourListRepository.save(travelVisitorTourList);
        } else {
            fileName = s3Provider.originalFileName(multipartFile);
            fileUrl = url + requestDto.tourList_title() + SEPARATOR + fileName;
            TravelVisitorTourList travelVisitorTourList =
                    travelVisitorTourListEntityMapper.toTravelVisitorTourList(requestDto,user,tourList,fileUrl);
            travelVisitorTourListRepository.save(travelVisitorTourList);
            s3Provider.createFolder(requestDto.tourList_title());
            fileUrl = requestDto.tourList_title() + SEPARATOR + fileName;
            s3Provider.saveFile(multipartFile,fileUrl);
        }
    }
}