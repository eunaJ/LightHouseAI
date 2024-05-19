package com.mju.lighthouseai.domain.travel_visitor_cafe.service.impl;

import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.cafe.exception.CafeErrorCode;
import com.mju.lighthouseai.domain.cafe.exception.NotFoundCafeException;
import com.mju.lighthouseai.domain.cafe.repository.CafeRepository;
import com.mju.lighthouseai.domain.travel.entity.Travel;
import com.mju.lighthouseai.domain.travel.exception.NotFoundTravelException;
import com.mju.lighthouseai.domain.travel.exception.TravelErrorCode;
import com.mju.lighthouseai.domain.travel.repository.TravelRepository;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.request.TravelVisitorCafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.request.TravelVisitorCafeUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.response.TravelVisitorCafeReadAllServiceResponseDto;
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
import java.util.List;

@RequiredArgsConstructor
@Service
public class TravelVisitorCafeServiceImpl implements TravelVisitorCafeService {
    private final TravelVisitorCafeRepository travelVisitorCafeRepository;
    private final TravelVisitorCafeEntityMapper travelVisitorCafeEntityMapper;
    private final CafeRepository cafeRepository;
    private final S3Provider s3Provider;
    private final TravelRepository travelRepository;

    private final String SEPARATOR = "/";
    private final String url = "https://light-house-ai.s3.ap-northeast-2.amazonaws.com/";
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public void createTravelVisitorCafe(
        TravelVisitorCafeCreateServiceRequestDto requestDto,
        User user,
        Long travelId,
        MultipartFile multipartFile
    ) throws IOException {
        Travel travel = travelRepository.findById(travelId)
            .orElseThrow(()->new NotFoundTravelException(TravelErrorCode.NOT_FOUND_TRAVEL));
        Cafe cafe = cafeRepository.findCafeByTitle(requestDto.cafe_title())
                .orElseThrow(()->new NotFoundCafeException(CafeErrorCode.NOT_FOUND_CAFE));
        String fileName;
        String fileUrl;
        if (multipartFile == null || multipartFile.isEmpty()){
            fileUrl = null;
            TravelVisitorCafe travelVisitorCafe =
                    travelVisitorCafeEntityMapper.toTravelVisitorCafe(requestDto,fileUrl,user,cafe,travel);
            travelVisitorCafeRepository.save(travelVisitorCafe);
        } else {
            fileName = s3Provider.originalFileName(multipartFile);
            fileUrl = travel.getFolderName() + SEPARATOR + fileName;
            TravelVisitorCafe travelVisitorCafe =
                    travelVisitorCafeEntityMapper.toTravelVisitorCafe(requestDto,fileUrl,user,cafe,travel);
            travelVisitorCafeRepository.save(travelVisitorCafe);
            s3Provider.saveFile(multipartFile,fileUrl);
        }
    }

    @Transactional
    public void updateTravelVisitorCafe(Long id, TravelVisitorCafeUpdateServiceRequestDto requestDto,MultipartFile multipartFile ,User user)
    throws IOException{
        TravelVisitorCafe travelVisitorCafe = findTravelVisitorCafe(id,user);
        Travel travel = travelRepository.findById(travelVisitorCafe.getTravel().getId())
            .orElseThrow(()->new NotFoundTravelException(TravelErrorCode.NOT_FOUND_TRAVEL));
        String folderName = travelVisitorCafe.getTravel().getFolderName();
        String fileUrl;
        Integer travel_expense = travel.getTravel_expense();
        travel_expense = travel_expense - travelVisitorCafe.getPrice();
        if (!requestDto.imageChange()){
            travelVisitorCafe.updateTravelVisitorCafe(
                requestDto.menu(),
                requestDto.price(),
                requestDto.content(),
                requestDto.opentime(),
                requestDto.closetime(),
                requestDto.location(),
                travelVisitorCafe.getImage_url());
            travel.updateExpense(travel_expense+requestDto.price());
        }else {
            fileUrl = s3Provider.updateImage(travelVisitorCafe.getImage_url(),folderName,multipartFile);
            travelVisitorCafe.updateTravelVisitorCafe(
               requestDto.menu(),
                requestDto.price(),
                requestDto.content(),
                requestDto.opentime(),
                requestDto.closetime(),
                requestDto.location(),
                fileUrl
            );
            travel.updateExpense(travel_expense+requestDto.price());
        }
    }
    private TravelVisitorCafe findTravelVisitorCafe(Long id,User user){
        return travelVisitorCafeRepository.findByIdAndUser(id,user)
                .orElseThrow(()-> new NotFoundTravelVisitorCafeException(TravelVisitorCafeErrorCode.NOT_FOUND_TRAVEL_VISITOR_CAFE));
    }

    public void deleteTravelVisitorCafe(Long id, User user) {
        TravelVisitorCafe travelVisitorCafe = travelVisitorCafeRepository.findById(id)
                .orElseThrow(() -> new NotFoundTravelVisitorCafeException(TravelVisitorCafeErrorCode.NOT_FOUND_TRAVEL_VISITOR_CAFE));
        travelVisitorCafeRepository.delete(travelVisitorCafe);
    }

    public TravelVisitorCafeReadAllServiceResponseDto readTravelVisitorCafe(Long id){
        TravelVisitorCafe travelVisitorCafe = travelVisitorCafeRepository.findById(id).
                orElseThrow(()->new NotFoundTravelVisitorCafeException(TravelVisitorCafeErrorCode.NOT_FOUND_TRAVEL_VISITOR_CAFE));
        return travelVisitorCafeEntityMapper.toTravelVisitorCafeReadResponseDto(travelVisitorCafe);
    }

    public List<TravelVisitorCafeReadAllServiceResponseDto> readAllTravelVisitorCafes(){
        List<TravelVisitorCafe> travelVisitorCafes = travelVisitorCafeRepository.findAll();
        return travelVisitorCafeEntityMapper.toTravelVisitorCafeReadAllResponseDto(travelVisitorCafes);
    }

    public List<TravelVisitorCafeReadAllServiceResponseDto> readAllTravelVisitorCafesByTravelId(Long id){
        List<TravelVisitorCafe> travelVisitorCafes = travelVisitorCafeRepository.findAllByTravelId(id);
        return travelVisitorCafeEntityMapper.toTravelVisitorCafeReadAllResponseDto(travelVisitorCafes);
    }
}
