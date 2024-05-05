package com.mju.lighthouseai.domain.travel_visitor_cafe.service.impl;

import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.cafe.exception.CafeErrorCode;
import com.mju.lighthouseai.domain.cafe.exception.NotFoundCafeException;
import com.mju.lighthouseai.domain.cafe.repository.CafeRepository;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.TravelVisitorCafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.TravelVisitorCafeUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.entity.TravelVisitorCafe;
import com.mju.lighthouseai.domain.travel_visitor_cafe.exception.NotFoundTravelVisitorCafeException;
import com.mju.lighthouseai.domain.travel_visitor_cafe.exception.TravelVisitorCafeErrorCode;
import com.mju.lighthouseai.domain.travel_visitor_cafe.mapper.service.TravelVisitorCafeEntityMapper;
import com.mju.lighthouseai.domain.travel_visitor_cafe.repository.TravelVisitorCafeRepository;
import com.mju.lighthouseai.domain.travel_visitor_cafe.service.TravelVisitorCafeService;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.global.s3.S3Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class TravelVisitorCafeServiceImpl implements TravelVisitorCafeService {
    private final TravelVisitorCafeRepository travelVisitorCafeRepository;
    private final TravelVisitorCafeEntityMapper travelVisitorCafeEntityMapper;
    private final CafeRepository cafeRepository;
    private final S3Provider s3Provider;

    private final String SEPARATOR = "/";
    private final String url = "https://light-house-ai.s3.ap-northeast-2.amazonaws.com/";
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public void createTravelVisitorCafe(TravelVisitorCafeCreateServiceRequestDto requestDto,
                                        User user,
                                        MultipartFile multipartFile
    ) throws IOException {
        String fileName;
        String fileUrl;
        Cafe cafe = cafeRepository.findCafeByTitle(requestDto.cafe_title())
                .orElseThrow(()->new NotFoundCafeException(CafeErrorCode.NOT_FOUND_CAFE));
        if (multipartFile == null || multipartFile.isEmpty()){
            fileUrl = null;
            TravelVisitorCafe travelVisitorCafe =
                    travelVisitorCafeEntityMapper.toTravelVisitorCafe(requestDto,user,cafe,fileUrl);
            travelVisitorCafeRepository.save(travelVisitorCafe);
        } else {
            fileName = s3Provider.originalFileName(multipartFile);
            fileUrl = url + requestDto.cafe_title() + SEPARATOR + fileName;
            TravelVisitorCafe travelVisitorCafe =
                    travelVisitorCafeEntityMapper.toTravelVisitorCafe(requestDto,user,cafe,fileUrl);
            travelVisitorCafeRepository.save(travelVisitorCafe);
            s3Provider.createFolder(requestDto.cafe_title());
            fileUrl = requestDto.cafe_title() + SEPARATOR + fileName;
            s3Provider.saveFile(multipartFile,fileUrl);
        }
    }

    @Transactional
    public void updateTravelVisitorCafe(Long id, TravelVisitorCafeUpdateServiceRequestDto requestDto, User user){
        TravelVisitorCafe travelVisitorCafe = findTravelVisitorCafe(id);
        String fileName;
        String fileUrl;
        fileUrl = null;
        log.info(String.valueOf(travelVisitorCafe.getCost()));
        // 카페가 없어져도 방문 기록은 남아야
        travelVisitorCafe.updateTravelVisitorCafe(requestDto.cost(), requestDto.opentime(),
                requestDto.closetime(), requestDto.location(), fileUrl);
    }
    private TravelVisitorCafe findTravelVisitorCafe(Long id){
        return travelVisitorCafeRepository.findById(id)
                .orElseThrow(()-> new NotFoundTravelVisitorCafeException(TravelVisitorCafeErrorCode.NOT_FOUND_TRAVEL_VISITOR_CAFE));
    }

    public void deleteTravelVisitorCafe(Long id, User user) {
        TravelVisitorCafe travelVisitorCafe = travelVisitorCafeRepository.findById(id)
                .orElseThrow(() -> new NotFoundTravelVisitorCafeException(TravelVisitorCafeErrorCode.NOT_FOUND_TRAVEL_VISITOR_CAFE));
        travelVisitorCafeRepository.delete(travelVisitorCafe);
    }
}
