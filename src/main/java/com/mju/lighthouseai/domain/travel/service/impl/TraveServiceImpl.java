package com.mju.lighthouseai.domain.travel.service.impl;

import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.cafe.exception.CafeErrorCode;
import com.mju.lighthouseai.domain.cafe.exception.NotFoundCafeException;
import com.mju.lighthouseai.domain.cafe.repository.CafeRepository;
import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.constituency.exception.ConstituencyErrorCode;
import com.mju.lighthouseai.domain.constituency.exception.NotFoundConstituencyException;
import com.mju.lighthouseai.domain.constituency.repository.ConstituencyRepository;
import com.mju.lighthouseai.domain.travel.dto.service.request.TravelCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel.dto.service.request.TravelUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.travel.dto.service.response.TravelVisitorCafeReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.travel.entity.Travel;
import com.mju.lighthouseai.domain.travel.exception.NotFoundTravelException;
import com.mju.lighthouseai.domain.travel.exception.TravelErrorCode;
import com.mju.lighthouseai.domain.travel.mapper.service.TravelEntityMapper;
import com.mju.lighthouseai.domain.travel.repository.TravelRepository;
import com.mju.lighthouseai.domain.travel.service.TravelService;
import com.mju.lighthouseai.domain.travel_visitor_cafe.dto.service.request.TravelVisitorCafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_cafe.entity.TravelVisitorCafe;
import com.mju.lighthouseai.domain.travel_visitor_cafe.mapper.service.TravelVisitorCafeEntityMapper;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.global.s3.S3Provider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class TraveServiceImpl implements TravelService {
    private final TravelRepository travelRepository;
    private final TravelEntityMapper travelEntityMapper;
    private final S3Provider s3Provider;
    private final ConstituencyRepository constituencyRepository;
    private final TravelVisitorCafeEntityMapper travelVisitorCafeEntityMapper;
    private final CafeRepository cafeRepository;

    private final String SEPARATOR = "/";
    private final String url = "https://light-house-ai.s3.ap-northeast-2.amazonaws.com/";
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public void createTravel(
        final TravelCreateServiceRequestDto requestDto,
        final MultipartFile travelImage,
        final List<TravelVisitorCafeCreateServiceRequestDto> travelVisitorCafeCreateServiceRequestDtos,
        final List<MultipartFile> travelVisitorCafeImages,
        final User user
    ) throws IOException {
        Constituency constituency = constituencyRepository.findByConstituency(requestDto.constituency())
            .orElseThrow(()->new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
        String travelFolderName = requestDto.title()+ UUID.randomUUID();
        String travelImageName = travelFolderName+SEPARATOR+s3Provider.originalFileName(travelImage);
        Travel travel = travelEntityMapper.toTravel(requestDto,travelFolderName,s3Provider.getImagePath(travelImageName),user,constituency);
        List<TravelVisitorCafe> travelVisitorCafes = new ArrayList<>();
        List<String> travelVisitorCafeImageNames = new ArrayList<>();
        for (int i = 0; i < travelVisitorCafeCreateServiceRequestDtos.size(); i++) {
            Cafe cafe = cafeRepository.findCafeByTitle(travelVisitorCafeCreateServiceRequestDtos.get(i).cafe_title())
                .orElseThrow(()->new NotFoundCafeException(CafeErrorCode.NOT_FOUND_CAFE));
            System.out.println(travelVisitorCafeImages.size());
                String travelVisitorCafeImageName =
                    s3Provider.originalFileName(travelVisitorCafeImages.get(i));
                String travelVisitorCafeImageUrl = null;
                if (!travelVisitorCafeImageName.isEmpty()) {
                    travelVisitorCafeImageUrl = s3Provider
                        .getImagePath(travelFolderName + S3Provider.SEPARATOR + travelVisitorCafeImageName);
                }
                travelVisitorCafeImageNames.add(travelVisitorCafeImageName);
                travelVisitorCafes.add(travelVisitorCafeEntityMapper.toTravelVisitorCafe(
                    travelVisitorCafeCreateServiceRequestDtos.get(i), travelVisitorCafeImageUrl,user,cafe ,travel));
        }
        travel.getTravelVisitorCafes().addAll(travelVisitorCafes);
        travelRepository.save(travel);
        saveImage(travel.getFolderName(),travelImage,travelImageName,travelVisitorCafeImages,travelVisitorCafeImageNames);
        System.out.println("끝");
    }
    private void saveImage(
        String travelFolderName,
        MultipartFile travelImage,
        String recipeImageName,
        List<MultipartFile> travelVisitorCafeImage,
        List<String> travelVisitorCafeImageName
    ) throws IOException {
        s3Provider.createFolder(travelFolderName);
        s3Provider.saveFile(travelImage, recipeImageName);
        for (int i = 0; i < travelVisitorCafeImage.size(); i++) {
            if (!travelVisitorCafeImage.get(i).isEmpty()) {
                s3Provider.saveFile(travelVisitorCafeImage.get(i),
                    travelFolderName + S3Provider.SEPARATOR + travelVisitorCafeImageName.get(i));
            }
        }
    }
    @Transactional
    public void updateTravel(
        Long id,
        TravelUpdateServiceRequestDto requestDto,
        User user,
        MultipartFile multipartFile)throws  IOException{
        Travel travel = travelRepository.findByIdAndUser(id, user)
            .orElseThrow(()->new NotFoundTravelException(TravelErrorCode.NOT_FOUND_TRAVEL));
        String folderName = travel.getFolderName();
        String fileUrl;
        Constituency constituency = findConstituency(requestDto.constituency());
        if (requestDto.imageChange()){
            travel.UpdateTravel(requestDto.title(), requestDto.travel_expense(),travel.getImage_url(),
                requestDto.serving(), requestDto.star(),constituency);
        }
        fileUrl = s3Provider.updateImage(travel.getImage_url(),folderName,multipartFile);
        travel.UpdateTravel(
            requestDto.title(),
            requestDto.travel_expense(),
            fileUrl,
            travel.getServing(),
            travel.getStar(),
            constituency
        );

    }
    private Travel findTravel(Long id){
        return travelRepository.findById(id)
                .orElseThrow(()-> new NotFoundTravelException(TravelErrorCode.NOT_FOUND_TRAVEL));
    }
    private Constituency findConstituency(String constituency_name){
        return constituencyRepository.findByConstituency(constituency_name)
            .orElseThrow(()->new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
    }

    public void deleteTravel(Long id, User user) {
        Travel travel = findTravel(id);
        travelRepository.delete(travel);
    }
/*
    public TravelVisitorCafeReadAllServiceResponseDto readTravelVisitorCafe(Long id){
        TravelVisitorCafe travelVisitorCafe = travelRepository.findById(id).
                orElseThrow(()->new NotFoundTravelException(TravelErrorCode.NOT_FOUND_TRAVEL_VISITOR_CAFE));
        return travelVisitorCafeEntityMapper.toTravelVisitorCafeReadResponseDto(travelVisitorCafe);
    }

    public List<TravelVisitorCafeReadAllServiceResponseDto> readAllTravelVisitorCafes(){
        List<TravelVisitorCafe> travelVisitorCafes = travelRepository.findAll();
        return travelVisitorCafeEntityMapper.toTravelVisitorCafeReadAllResponseDto(travelVisitorCafes);
    }
*/
}
