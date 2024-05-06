package com.mju.lighthouseai.domain.travel_visitor_shoppingmall.service.impl;

import com.mju.lighthouseai.domain.shoppingmall.entity.ShoppingMall;
import com.mju.lighthouseai.domain.shoppingmall.exception.NotFoundShoppingMallException;
import com.mju.lighthouseai.domain.shoppingmall.exception.ShoppingMallErrorCode;
import com.mju.lighthouseai.domain.shoppingmall.repository.ShoppingMallRepository;
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

@RequiredArgsConstructor
@Service
public class TravelVisitorShoppingMallServiceImpl {
    private final TravelVisitorShoppingMallRepository travelVisitorShoppingMallRepository;
    private final TravelVisitorShoppingMallEntityMapper travelVisitorShoppingMallEntityMapper;
    private final ShoppingMallRepository shoppingMallRepository;
    private final S3Provider s3Provider;

    private final String SEPARATOR = "/";
    private final String url = "https://light-house-ai.s3.ap-northeast-2.amazonaws.com/";
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public void createTravelVisitorShoppingMall(TravelVisitorShoppingMallCreateServiceRequestDto requestDto,
                                                User user,
                                                MultipartFile multipartFile
    ) throws IOException {
        String fileName;
        String fileUrl;
        ShoppingMall shoppingMall = shoppingMallRepository.findShoppingMallByTitle(
                requestDto.shoppingMall_title())
                .orElseThrow(()->new NotFoundShoppingMallException(ShoppingMallErrorCode.NOT_FOUND_ShoppingMall));
        if (multipartFile == null || multipartFile.isEmpty()){
            fileUrl = null;
            TravelVisitorShoppingMall travelVisitorShoppingMall =
                    travelVisitorShoppingMallEntityMapper.toTravelVisitorShoppingMall(
                            requestDto,user,shoppingMall,fileUrl);
            travelVisitorShoppingMallRepository.save(travelVisitorShoppingMall);
        } else {
            fileName = s3Provider.originalFileName(multipartFile);
            fileUrl = url + requestDto.shoppingMall_title() + SEPARATOR + fileName;
            TravelVisitorShoppingMall travelVisitorShoppingMall =
                    travelVisitorShoppingMallEntityMapper.toTravelVisitorShoppingMall(requestDto,user,shoppingMall,fileUrl);
            travelVisitorShoppingMallRepository.save(travelVisitorShoppingMall);
            s3Provider.createFolder(requestDto.shoppingMall_title());
            fileUrl = requestDto.shoppingMall_title() + SEPARATOR + fileName;
            s3Provider.saveFile(multipartFile,fileUrl);
        }
    }

    @Transactional
    public void updateTravelVisitorShoppingMall(Long id, TravelVisitorShoppingMallUpdateServiceRequestDto requestDto, User user){
        TravelVisitorShoppingMall travelVisitorShoppingMall = findTravelVisitorShoppingMall(id);
        String fileName;
        String fileUrl;
        fileUrl = null;
        // 없어져도 방문 기록은 남아야?
        travelVisitorShoppingMall.updateTravelVisitorShoppingMall(requestDto.price(),
                requestDto.opentime(), requestDto.closetime(), requestDto.location(), fileUrl);
    }
    private TravelVisitorShoppingMall findTravelVisitorShoppingMall(Long id){
        return travelVisitorShoppingMallRepository.findById(id)
                .orElseThrow(()-> new NotFoundTravelVisitorShoppingMallException(
                        TravelVisitorShoppingMallErrorCode.NOT_FOUND_TRAVEL_VISITOR_ShoppingMall));
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
}