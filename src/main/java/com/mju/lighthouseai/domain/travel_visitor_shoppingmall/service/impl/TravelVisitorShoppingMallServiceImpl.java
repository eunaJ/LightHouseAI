package com.mju.lighthouseai.domain.travel_visitor_shoppingmall.service.impl;

import com.mju.lighthouseai.domain.shoppingmall.entity.ShoppingMall;
import com.mju.lighthouseai.domain.shoppingmall.exception.NotFoundShoppingMallException;
import com.mju.lighthouseai.domain.shoppingmall.exception.ShoppingMallErrorCode;
import com.mju.lighthouseai.domain.shoppingmall.repository.ShoppingMallRepository;
import com.mju.lighthouseai.domain.travel.entity.Travel;
import com.mju.lighthouseai.domain.travel.exception.NotFoundTravelException;
import com.mju.lighthouseai.domain.travel.exception.TravelErrorCode;
import com.mju.lighthouseai.domain.travel.repository.TravelRepository;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.service.request.TravelVisitorShoppingMallCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.service.request.TravelVisitorShoppingMallUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.service.response.TravelVisitorShoppingMallReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.entity.TravelVisitorShoppingMall;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.exception.NotFoundTravelVisitorShoppingMallException;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.exception.TravelVisitorShoppingMallErrorCode;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.mapper.service.TravelVisitorShoppingMallEntityMapper;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.repository.TravelVisitorShoppingMallRepository;
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
public class TravelVisitorShoppingMallServiceImpl {
    private final TravelVisitorShoppingMallRepository travelVisitorShoppingMallRepository;
    private final TravelVisitorShoppingMallEntityMapper travelVisitorShoppingMallEntityMapper;
    private final ShoppingMallRepository shoppingMallRepository;
    private final S3Provider s3Provider;
    private final TravelRepository travelRepository;

    private final String SEPARATOR = "/";
    private final String url = "https://light-house-ai.s3.ap-northeast-2.amazonaws.com/";
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public void createTravelVisitorShoppingMall(
        Long id,
        TravelVisitorShoppingMallCreateServiceRequestDto requestDto,
        User user,
        MultipartFile multipartFile
    ) throws IOException {
        String fileName;
        String fileUrl;
        Travel travel = travelRepository.findById(id).orElseThrow(
            () -> new NotFoundTravelException(TravelErrorCode.NOT_FOUND_TRAVEL)
        );
        ShoppingMall shoppingMall = shoppingMallRepository.findShoppingMallByTitle(
                requestDto.shoppingMall_title())
                .orElseThrow(()->new NotFoundShoppingMallException(ShoppingMallErrorCode.NOT_FOUND_ShoppingMall));
        if (multipartFile == null || multipartFile.isEmpty()){
            fileUrl = null;
            TravelVisitorShoppingMall travelVisitorShoppingMall =
                    travelVisitorShoppingMallEntityMapper.toTravelVisitorShoppingMall(
                            requestDto,fileUrl,user,shoppingMall,travel);
            travelVisitorShoppingMallRepository.save(travelVisitorShoppingMall);
        } else {
            fileName = s3Provider.originalFileName(multipartFile);
            fileUrl = url + requestDto.shoppingMall_title() + SEPARATOR + fileName;
            TravelVisitorShoppingMall travelVisitorShoppingMall =
                    travelVisitorShoppingMallEntityMapper.toTravelVisitorShoppingMall(requestDto,fileUrl,user,shoppingMall,travel);
            travelVisitorShoppingMallRepository.save(travelVisitorShoppingMall);
            s3Provider.createFolder(requestDto.shoppingMall_title());
            fileUrl = requestDto.shoppingMall_title() + SEPARATOR + fileName;
            s3Provider.saveFile(multipartFile,fileUrl);
        }
    }

    @Transactional
    public void updateTravelVisitorShoppingMall(
        Long id,
        TravelVisitorShoppingMallUpdateServiceRequestDto requestDto,
        MultipartFile multipartFile ,
        User user)throws IOException{
        TravelVisitorShoppingMall travelVisitorShoppingMall = travelVisitorShoppingMallRepository.findByIdAndUser(id,user)
            .orElseThrow(()->new NotFoundTravelVisitorShoppingMallException(ShoppingMallErrorCode.NOT_FOUND_ShoppingMall));
        String folderName = travelVisitorShoppingMall.getTravel().getFolderName();
        String fileUrl;
        if (!requestDto.imageChange()){
            travelVisitorShoppingMall.updateTravelVisitorShoppingMall(
                requestDto.price(),
                requestDto.content(),
                requestDto.opentime(),
                requestDto.closetime(),
                requestDto.location(),
                travelVisitorShoppingMall.getImage_url());
        }else {
            fileUrl = s3Provider.updateImage(travelVisitorShoppingMall.getImage_url(),folderName,multipartFile);
            travelVisitorShoppingMall.updateTravelVisitorShoppingMall(
                requestDto.price(),
                requestDto.content(),
                requestDto.opentime(),
                requestDto.closetime(),
                requestDto.location(),
                fileUrl
            );
    }

    }

    public void deleteTravelVisitorShoppingMall(Long id, User user) {
        TravelVisitorShoppingMall travelVisitorShoppingMall = travelVisitorShoppingMallRepository.findById(id)
                .orElseThrow(() -> new NotFoundTravelVisitorShoppingMallException(
                        TravelVisitorShoppingMallErrorCode.NOT_FOUND_TRAVEL_VISITOR_ShoppingMall));
        travelVisitorShoppingMallRepository.delete(travelVisitorShoppingMall);
    }

    public TravelVisitorShoppingMallReadAllServiceResponseDto readTravelVisitorShoppingMall(Long id){
        TravelVisitorShoppingMall travelVisitorShoppingMall = travelVisitorShoppingMallRepository.findById(id).
                orElseThrow(()->new NotFoundTravelVisitorShoppingMallException(
                        TravelVisitorShoppingMallErrorCode.NOT_FOUND_TRAVEL_VISITOR_ShoppingMall));
        return travelVisitorShoppingMallEntityMapper.toTravelVisitorShoppingMallReadResponseDto(
                travelVisitorShoppingMall);
    }

    public List<TravelVisitorShoppingMallReadAllServiceResponseDto> readAllTravelVisitorShoppingMalls(){
        List<TravelVisitorShoppingMall> travelVisitorShoppingMalls = travelVisitorShoppingMallRepository.findAll();
        return travelVisitorShoppingMallEntityMapper.toTravelVisitorShoppingMallReadAllResponseDto(
                travelVisitorShoppingMalls);
    }

    public List<TravelVisitorShoppingMallReadAllServiceResponseDto> readAllTravelVisitorShoppingMallsByTravelId(Long id){
        List<TravelVisitorShoppingMall> travelVisitorShoppingMalls = travelVisitorShoppingMallRepository.findAllByTravelId(id);
        return travelVisitorShoppingMallEntityMapper.toTravelVisitorShoppingMallReadAllResponseDto(
                travelVisitorShoppingMalls);
    }
}