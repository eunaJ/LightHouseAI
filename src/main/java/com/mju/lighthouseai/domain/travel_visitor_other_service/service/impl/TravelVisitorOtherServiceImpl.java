package com.mju.lighthouseai.domain.travel_visitor_other_service.service.impl;

import com.mju.lighthouseai.domain.other_service.entity.OtherServiceEntity;
import com.mju.lighthouseai.domain.other_service.exception.NotFoundOtherServiceException;
import com.mju.lighthouseai.domain.other_service.exception.OtherServiceErrorCode;
import com.mju.lighthouseai.domain.other_service.repository.OtherServiceRepository;
import com.mju.lighthouseai.domain.travel.entity.Travel;
import com.mju.lighthouseai.domain.travel.exception.NotFoundTravelException;
import com.mju.lighthouseai.domain.travel.exception.TravelErrorCode;
import com.mju.lighthouseai.domain.travel.repository.TravelRepository;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.response.TravelVisitorCafeReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.entity.TravelVisitorCafe;
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
    private final TravelRepository travelRepository;

    private final String SEPARATOR = "/";
    private final String url = "https://light-house-ai.s3.ap-northeast-2.amazonaws.com/";
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public void createTravelVisitorOtherService(
            Long id,
            TravelVisitorOtherServiceCreateServiceRequestDto requestDto,
            User user,
            MultipartFile multipartFile
    ) throws IOException {
        String fileName;
        String fileUrl;
        Travel travel = travelRepository.findById(id)
            .orElseThrow(()->new NotFoundTravelException(TravelErrorCode.NOT_FOUND_TRAVEL));
        OtherServiceEntity otherServiceEntity = otherServiceRepository
                .findOtherServiceEntitieByTitle(requestDto.otherService_title())
                .orElseThrow(()->new NotFoundOtherServiceException(
                        OtherServiceErrorCode.NOT_FOUND_OtherService));
        if (multipartFile == null || multipartFile.isEmpty()){
            fileUrl = null;
            TravelVisitorOtherServiceEntity travelVisitorOtherServiceEntity =
                    travelVisitorOtherServiceEntityMapper.toTravelVisitorOtherService(
                            requestDto,fileUrl,user,otherServiceEntity,travel);
            travelVisitorOtherServiceRepository.save(travelVisitorOtherServiceEntity);
        } else {
            fileName = s3Provider.originalFileName(multipartFile);
            fileUrl = url + requestDto.otherService_title() + SEPARATOR + fileName;
            TravelVisitorOtherServiceEntity travelVisitorOtherServiceEntity =
                    travelVisitorOtherServiceEntityMapper.toTravelVisitorOtherService(
                            requestDto,fileUrl,user,otherServiceEntity,travel);
            travelVisitorOtherServiceRepository.save(travelVisitorOtherServiceEntity);
            s3Provider.createFolder(requestDto.otherService_title());
            fileUrl = requestDto.otherService_title() + SEPARATOR + fileName;
            s3Provider.saveFile(multipartFile,fileUrl);
        }
    }

    @Transactional
    public void updateTravelVisitorOtherService(
        Long id,
        TravelVisitorOtherServiceUpdateServiceRequestDto requestDto,
        MultipartFile multipartFile ,
        User user) throws IOException {
        TravelVisitorOtherServiceEntity travelVisitorOtherServiceEntity = travelVisitorOtherServiceRepository.findByIdAndUser(id,user)
            .orElseThrow(()->new NotFoundTravelVisitorOtherServiceException(TravelVisitorOtherServiceErrorCode.NOT_FOUND_TRAVEL_VISITOR_OtherService));
        Travel travel = travelRepository.findById(travelVisitorOtherServiceEntity.getTravel().getId())
            .orElseThrow(()->new NotFoundTravelException(TravelErrorCode.NOT_FOUND_TRAVEL));
        String folderName = travelVisitorOtherServiceEntity.getTravel().getFolderName();
        String fileUrl;
        Integer travel_expense = travel.getTravel_expense() - travelVisitorOtherServiceEntity.getPrice();
        if(!requestDto.imageChange()){
          travelVisitorOtherServiceEntity.updateTravelVisitorOtherServiceEntity(
              requestDto.price(),
              requestDto.content(),
              requestDto.opentime(),
              requestDto.closetime(),
              requestDto.location(),
              travelVisitorOtherServiceEntity.getImage_url()
          );
          travel.updateExpense(travel_expense+requestDto.price());
        }else {
            fileUrl = s3Provider.updateImage(travelVisitorOtherServiceEntity.getImage_url(),folderName,multipartFile);
            travelVisitorOtherServiceEntity.updateTravelVisitorOtherServiceEntity(
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

    public List<TravelVisitorOtherServiceReadAllServiceResponseDto> readAllTravelVisitorOtherServicesByTravelId(Long id){
        List<TravelVisitorOtherServiceEntity> travelVisitorOtherServiceEntities
                = travelVisitorOtherServiceRepository.findAllByTravelId(id);
        return travelVisitorOtherServiceEntityMapper.toTravelVisitorOtherServiceReadAllResponseDto(
                travelVisitorOtherServiceEntities);
    }
}