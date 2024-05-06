package com.mju.lighthouseai.domain.travel_visitor_other_service.service.impl;

import com.mju.lighthouseai.domain.other_service.entity.OtherServiceEntity;
import com.mju.lighthouseai.domain.other_service.exception.NotFoundOtherServiceException;
import com.mju.lighthouseai.domain.other_service.exception.OtherServiceErrorCode;
import com.mju.lighthouseai.domain.other_service.repository.OtherServiceRepository;
import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.service.request.TravelVisitorOtherServiceCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.service.request.TravelVisitorOtherServiceUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.service.response.TravelVisitorOtherServiceReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.travel_visitor_other_service.entity.TravelVisitorOtherServiceEntity;
import com.mju.lighthouseai.domain.travel_visitor_other_service.exception.NotFoundTravelVisitorOtherServiceException;
import com.mju.lighthouseai.domain.travel_visitor_other_service.exception.TravelVisitorOtherServiceErrorCode;
import com.mju.lighthouseai.domain.travel_visitor_other_service.mapper.service.TravelVisitorOtherServiceEntityMapper;
import com.mju.lighthouseai.domain.travel_visitor_other_service.repository.TravelVisitorOtherServiceRepository;
import com.mju.lighthouseai.domain.travel_visitor_other_service.service.TravelVisitorOtherService;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.global.s3.S3Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RequiredArgsConstructor
@Service
public class TravelVisitorOtherServiceImpl implements TravelVisitorOtherService {
    private final TravelVisitorOtherServiceRepository travelVisitorOtherServiceRepository;
    private final TravelVisitorOtherServiceEntityMapper travelVisitorOtherServiceEntityMapper;
    private final OtherServiceRepository otherServiceRepository;
    private final S3Provider s3Provider;

    private final String SEPARATOR = "/";
    private final String url = "https://light-house-ai.s3.ap-northeast-2.amazonaws.com/";
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public void createTravelVisitorOtherService(
            TravelVisitorOtherServiceCreateServiceRequestDto requestDto, User user,
            MultipartFile multipartFile
    ) throws IOException {
        String fileName;
        String fileUrl;
        OtherServiceEntity otherServiceEntity = otherServiceRepository
                .findOtherServiceEntitieByTitle(requestDto.otherService_title())
                .orElseThrow(()->new NotFoundOtherServiceException(
                        OtherServiceErrorCode.NOT_FOUND_OtherService));
        if (multipartFile == null || multipartFile.isEmpty()){
            fileUrl = null;
            TravelVisitorOtherServiceEntity travelVisitorOtherServiceEntity =
                    travelVisitorOtherServiceEntityMapper.toTravelVisitorOtherService(
                            requestDto,user,otherServiceEntity,fileUrl);
            travelVisitorOtherServiceRepository.save(travelVisitorOtherServiceEntity);
        } else {
            fileName = s3Provider.originalFileName(multipartFile);
            fileUrl = url + requestDto.otherService_title() + SEPARATOR + fileName;
            TravelVisitorOtherServiceEntity travelVisitorOtherServiceEntity =
                    travelVisitorOtherServiceEntityMapper.toTravelVisitorOtherService(
                            requestDto,user,otherServiceEntity,fileUrl);
            travelVisitorOtherServiceRepository.save(travelVisitorOtherServiceEntity);
            s3Provider.createFolder(requestDto.otherService_title());
            fileUrl = requestDto.otherService_title() + SEPARATOR + fileName;
            s3Provider.saveFile(multipartFile,fileUrl);
        }
    }

    @Transactional
    public void updateTravelVisitorOtherService(
            Long id, TravelVisitorOtherServiceUpdateServiceRequestDto requestDto, User user){
        TravelVisitorOtherServiceEntity travelVisitorOtherServiceEntity = findTravelVisitorOtherService(id);
        String fileName;
        String fileUrl;
        fileUrl = null;
        // 없어져도 방문 기록은 남아야?
        travelVisitorOtherServiceEntity.updateTravelVisitorOtherServiceEntity(requestDto.price(),
                requestDto.opentime(), requestDto.closetime(), requestDto.location(), fileUrl);
    }
    private TravelVisitorOtherServiceEntity findTravelVisitorOtherService(Long id){
        return travelVisitorOtherServiceRepository.findById(id)
                .orElseThrow(()-> new NotFoundTravelVisitorOtherServiceException(
                        TravelVisitorOtherServiceErrorCode.NOT_FOUND_TRAVEL_VISITOR_OtherService));
    }

    public void deleteTravelVisitorOtherService(Long id, User user) {
        TravelVisitorOtherServiceEntity travelVisitorOtherServiceEntity =
                travelVisitorOtherServiceRepository.findById(id)
                .orElseThrow(()-> new NotFoundTravelVisitorOtherServiceException(
                        TravelVisitorOtherServiceErrorCode.NOT_FOUND_TRAVEL_VISITOR_OtherService));
        travelVisitorOtherServiceRepository.delete(travelVisitorOtherServiceEntity);
    }

    public TravelVisitorOtherServiceReadAllServiceResponseDto readTravelVisitorOtherService(Long id){
        TravelVisitorOtherServiceEntity travelVisitorOtherServiceEntity =
                travelVisitorOtherServiceRepository.findById(id)
                        .orElseThrow(()-> new NotFoundTravelVisitorOtherServiceException(
                                TravelVisitorOtherServiceErrorCode.NOT_FOUND_TRAVEL_VISITOR_OtherService));
        return travelVisitorOtherServiceEntityMapper.toTravelVisitorOtherServiceReadResponseDto(
                travelVisitorOtherServiceEntity);
    }

    public List<TravelVisitorOtherServiceReadAllServiceResponseDto> readAllTravelVisitorOtherServices(){
        List<TravelVisitorOtherServiceEntity> travelVisitorOtherServiceEntities
                = travelVisitorOtherServiceRepository.findAll();
        return travelVisitorOtherServiceEntityMapper.toTravelVisitorOtherServiceReadAllResponseDto(
                travelVisitorOtherServiceEntities);
    }
}