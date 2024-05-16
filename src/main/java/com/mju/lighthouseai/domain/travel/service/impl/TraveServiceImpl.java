package com.mju.lighthouseai.domain.travel.service.impl;

import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.cafe.exception.CafeErrorCode;
import com.mju.lighthouseai.domain.cafe.exception.NotFoundCafeException;
import com.mju.lighthouseai.domain.cafe.repository.CafeRepository;
import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.constituency.exception.ConstituencyErrorCode;
import com.mju.lighthouseai.domain.constituency.exception.NotFoundConstituencyException;
import com.mju.lighthouseai.domain.constituency.repository.ConstituencyRepository;
import com.mju.lighthouseai.domain.other_service.entity.OtherServiceEntity;
import com.mju.lighthouseai.domain.other_service.exception.NotFoundOtherServiceException;
import com.mju.lighthouseai.domain.other_service.exception.OtherServiceErrorCode;
import com.mju.lighthouseai.domain.other_service.mapper.service.OtherServiceEntityMapper;
import com.mju.lighthouseai.domain.other_service.repository.OtherServiceRepository;
import com.mju.lighthouseai.domain.other_service.service.OtherService;
import com.mju.lighthouseai.domain.restaurant.entity.Restaurant;
import com.mju.lighthouseai.domain.restaurant.exceoption.NotFoundRestaurantException;
import com.mju.lighthouseai.domain.restaurant.exceoption.RestaurantErrorCode;
import com.mju.lighthouseai.domain.restaurant.repository.RestaurantRepository;
import com.mju.lighthouseai.domain.shoppingmall.entity.ShoppingMall;
import com.mju.lighthouseai.domain.shoppingmall.exception.NotFoundShoppingMallException;
import com.mju.lighthouseai.domain.shoppingmall.exception.ShoppingMallErrorCode;
import com.mju.lighthouseai.domain.shoppingmall.repository.ShoppingMallRepository;
import com.mju.lighthouseai.domain.tour_list.entity.TourList;
import com.mju.lighthouseai.domain.tour_list.exceoption.NotFoundTourListException;
import com.mju.lighthouseai.domain.tour_list.exceoption.TourListErrorCode;
import com.mju.lighthouseai.domain.tour_list.repository.TourListRepository;
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
import com.mju.lighthouseai.domain.travel_visitor_other_service.dto.service.request.TravelVisitorOtherServiceCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_other_service.entity.TravelVisitorOtherServiceEntity;
import com.mju.lighthouseai.domain.travel_visitor_other_service.mapper.service.TravelVisitorOtherServiceEntityMapper;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.request.TravelVisitorRestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.entity.TravelVisitorRestaurant;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.mapper.service.TravelVisitorRestaurantEntityMapper;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.dto.service.request.TravelVisitorShoppingMallCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.entity.TravelVisitorShoppingMall;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.mapper.service.TravelVisitorShoppingMallEntityMapper;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.dto.service.request.TravelVisitorTourListCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.entity.TravelVisitorTourList;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.mapper.service.TravelVisitorTourListEntityMapper;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.domain.user.exception.NotFoundUserException;
import com.mju.lighthouseai.domain.user.exception.UserErrorCode;
import com.mju.lighthouseai.domain.user.repository.UserRepository;
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
    private final RestaurantRepository restaurantRepository;
    private final TravelVisitorRestaurantEntityMapper travelVisitorRestaurantEntityMapper;
    private final ShoppingMallRepository shoppingMallRepository;
    private final TravelVisitorShoppingMallEntityMapper travelVisitorShoppingMallEntityMapper;
    private final TourListRepository tourListRepository;
    private final TravelVisitorTourListEntityMapper travelVisitorTourListEntityMapper;
    private final OtherServiceRepository otherServiceRepository;
    private final TravelVisitorOtherServiceEntityMapper travelVisitorOtherServiceEntityMapper;
    private final UserRepository userRepository;

    private final String SEPARATOR = "/";
    private final String url = "https://light-house-ai.s3.ap-northeast-2.amazonaws.com/";
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public void createTravel(
        final TravelCreateServiceRequestDto requestDto,
        final MultipartFile travelImage,
        final List<TravelVisitorCafeCreateServiceRequestDto> travelVisitorCafeCreateServiceRequestDtos,
        final List<MultipartFile> travelVisitorCafeImages,
        final List<TravelVisitorRestaurantCreateServiceRequestDto> travelVisitorRestaurantCreateServiceRequestDtos,
        final List<MultipartFile> travelVisitorRestaurantImages,
        final List<TravelVisitorShoppingMallCreateServiceRequestDto> travelVisitorShoppingMallCreateServiceRequestDtos,
        final List<MultipartFile> travelVisitorShoppingMallImages,
        final List<TravelVisitorTourListCreateServiceRequestDto> travelVisitorTourListCreateServiceRequestDtos,
        final List<MultipartFile> travelVisitorTourListImages,
        final List<TravelVisitorOtherServiceCreateServiceRequestDto> travelVisitorOtherServiceCreateServiceRequestDtos,
        final List<MultipartFile> travelVisitorOtherServiceImages,
        final User user
    ) throws IOException {
        Constituency constituency = constituencyRepository.findByConstituency(requestDto.constituency())
            .orElseThrow(()->new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
        String travelFolderName = requestDto.title()+ UUID.randomUUID();
        String travelImageName = travelFolderName+SEPARATOR+s3Provider.originalFileName(travelImage);
        Travel travel = travelEntityMapper.toTravel(requestDto,travelFolderName,s3Provider.getImagePath(travelImageName),user,constituency);
        // 여행지_방문지_카페
        List<TravelVisitorCafe> travelVisitorCafes = new ArrayList<>();
        List<String> travelVisitorCafeImageNames = new ArrayList<>();
        //여행지_방문지_식당
        List<TravelVisitorRestaurant> travelVisitorRestaurants = new ArrayList<>();
        List<String> travelVisitorRestaurantImageNames = new ArrayList<>();
        //여행지_방문지_쇼핑몰
        List<TravelVisitorShoppingMall> travelVisitorShoppingMalls = new ArrayList<>();
        List<String> travelVisitorShoppingMallImageNames = new ArrayList<>();
        //여행지_방문지_관광지
        List<TravelVisitorTourList> travelVisitorTourLists = new ArrayList<>();
        List<String> travelVisitorTourListImageNames = new ArrayList<>();
        //여행지_방문지_기타서비스
        List<TravelVisitorOtherServiceEntity> travelVisitorOtherServices = new ArrayList<>();
        List<String> travelVisitorOtherServiceImageNames = new ArrayList<>();

        // 여행지 방문지 카페 생성
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
        //여행지_방문지_식당
        for (int i = 0; i < travelVisitorRestaurantCreateServiceRequestDtos.size(); i++) {
            Restaurant restaurant = restaurantRepository.findRestaurantByTitle(travelVisitorRestaurantCreateServiceRequestDtos.get(i).restaurant_title())
                .orElseThrow(()->new NotFoundRestaurantException(RestaurantErrorCode.NOT_FOUND_Restaurant));
            String travelVisitorRestaurantImageName =
                s3Provider.originalFileName(travelVisitorRestaurantImages.get(i));
            String travelVisitorRestaurantImageUrl = null;
            if (!travelVisitorRestaurantImageName.isEmpty()) {
                travelVisitorRestaurantImageUrl = s3Provider
                    .getImagePath(travelFolderName + S3Provider.SEPARATOR + travelVisitorRestaurantImageName);
            }
            travelVisitorRestaurantImageNames.add(travelVisitorRestaurantImageName);
            travelVisitorRestaurants.add(travelVisitorRestaurantEntityMapper.toTravelVisitorRestaurant(
                travelVisitorRestaurantCreateServiceRequestDtos.get(i), travelVisitorRestaurantImageUrl,user,restaurant ,travel));
        }
        // 여행지_방문지_쇼핑몰
        for (int i = 0; i < travelVisitorShoppingMallCreateServiceRequestDtos.size(); i++) {
            ShoppingMall shoppingMall = shoppingMallRepository.findShoppingMallByTitle(travelVisitorShoppingMallCreateServiceRequestDtos.get(i).shoppingMall_title())
                .orElseThrow(()->new NotFoundShoppingMallException(ShoppingMallErrorCode.NOT_FOUND_ShoppingMall));
            String travelVisitorShoppingMallImageName =
                s3Provider.originalFileName(travelVisitorShoppingMallImages.get(i));
            String travelVisitorShoppingMallImageUrl = null;
            if (!travelVisitorShoppingMallImageName.isEmpty()) {
                travelVisitorShoppingMallImageUrl = s3Provider
                    .getImagePath(travelFolderName + S3Provider.SEPARATOR + travelVisitorShoppingMallImageName);
            }
            travelVisitorShoppingMallImageNames.add(travelVisitorShoppingMallImageName);
            travelVisitorShoppingMalls.add(travelVisitorShoppingMallEntityMapper.toTravelVisitorShoppingMall(
                travelVisitorShoppingMallCreateServiceRequestDtos.get(i), travelVisitorShoppingMallImageUrl,user,shoppingMall ,travel));
        }
        //여행지_방문지_관광지
        for (int i = 0; i < travelVisitorTourListCreateServiceRequestDtos.size(); i++) {
            TourList tourList = tourListRepository.findTourListByTitle(travelVisitorTourListCreateServiceRequestDtos.get(i).tourList_title())
                .orElseThrow(()->new NotFoundTourListException(TourListErrorCode.NOT_FOUND_TOURLIST));
            String travelVisitorTourListsImageName =
                s3Provider.originalFileName(travelVisitorTourListImages.get(i));
            String travelVisitorTourListImageUrl = null;
            if (!travelVisitorTourListsImageName.isEmpty()) {
                travelVisitorTourListImageUrl = s3Provider
                    .getImagePath(travelFolderName + S3Provider.SEPARATOR + travelVisitorTourListsImageName);
            }
            travelVisitorTourListImageNames.add(travelVisitorTourListsImageName);
            travelVisitorTourLists.add(travelVisitorTourListEntityMapper.toTravelVisitorTourList(
                travelVisitorTourListCreateServiceRequestDtos.get(i), travelVisitorTourListImageUrl,user,tourList ,travel));
        }
        //여행지_방문지_기타서비스
        for (int i = 0; i < travelVisitorOtherServiceCreateServiceRequestDtos.size(); i++) {
            OtherServiceEntity otherService = otherServiceRepository.findOtherServiceEntitieByTitle(travelVisitorOtherServiceCreateServiceRequestDtos.get(i).otherService_title())
                .orElseThrow(()->new NotFoundOtherServiceException(OtherServiceErrorCode.NOT_FOUND_OtherService));
            String travelOtherServicesImageName =
                s3Provider.originalFileName(travelVisitorOtherServiceImages.get(i));
            String travelVisitorOtherSerivceImageUrl = null;
            if (!travelOtherServicesImageName.isEmpty()) {
                travelVisitorOtherSerivceImageUrl = s3Provider
                    .getImagePath(travelFolderName + S3Provider.SEPARATOR + travelOtherServicesImageName);
            }
            travelVisitorOtherServiceImageNames.add(travelOtherServicesImageName);
            travelVisitorOtherServices.add(travelVisitorOtherServiceEntityMapper.toTravelVisitorOtherService(
                travelVisitorOtherServiceCreateServiceRequestDtos.get(i), travelVisitorOtherSerivceImageUrl,user,otherService ,travel));
        }
        travel.getTravelVisitorCafes().addAll(travelVisitorCafes);
        travel.getTravelVisitorRestaurants().addAll(travelVisitorRestaurants);
        travel.getTravelVisitorShoppingMalls().addAll(travelVisitorShoppingMalls);
        travel.getTravelVisitorTourLists().addAll(travelVisitorTourLists);
        travel.getTravelVisitorOtherServiceEntities().addAll(travelVisitorOtherServices);
        travelRepository.save(travel);
        saveImage(travel.getFolderName(),travelImage,travelImageName,travelVisitorCafeImages,travelVisitorCafeImageNames,
            travelVisitorRestaurantImages,travelVisitorRestaurantImageNames,
            travelVisitorShoppingMallImages,travelVisitorShoppingMallImageNames,
            travelVisitorTourListImages,travelVisitorTourListImageNames,
            travelVisitorOtherServiceImages,travelVisitorOtherServiceImageNames);
        System.out.println("끝");
    }
    private void saveImage(
        String travelFolderName,
        MultipartFile travelImage,
        String recipeImageName,
        List<MultipartFile> travelVisitorCafeImage,
        List<String> travelVisitorCafeImageName,
        List<MultipartFile> travelVisitorRestaurantImage,
        List<String> travelVisitorRestaurantImageName,
        List<MultipartFile> travelVisitorShoppingMallImage,
        List<String> travelVisitorShoppingMallImageName,
        List<MultipartFile> travelVisitorTourListImage,
        List<String> travelVisitorTourListImageName,
        List<MultipartFile> travelVisitorOtherServiceImage,
        List<String> travelVisitorOtherServiceImageName
    ) throws IOException {
        s3Provider.createFolder(travelFolderName);
        s3Provider.saveFile(travelImage, recipeImageName);
        for (int i = 0; i < travelVisitorCafeImage.size(); i++) {
            if (!travelVisitorCafeImage.get(i).isEmpty()) {
                s3Provider.saveFile(travelVisitorCafeImage.get(i),
                    travelFolderName + S3Provider.SEPARATOR + travelVisitorCafeImageName.get(i));
            }
        }
        for (int i = 0; i < travelVisitorRestaurantImage.size(); i++) {
            if (!travelVisitorRestaurantImage.get(i).isEmpty()) {
                s3Provider.saveFile(travelVisitorRestaurantImage.get(i),
                    travelFolderName + S3Provider.SEPARATOR + travelVisitorRestaurantImageName.get(i));
            }
        }
        for (int i = 0; i < travelVisitorShoppingMallImage.size(); i++) {
            if (!travelVisitorShoppingMallImage.get(i).isEmpty()) {
                s3Provider.saveFile(travelVisitorShoppingMallImage.get(i),
                    travelFolderName + S3Provider.SEPARATOR + travelVisitorShoppingMallImageName.get(i));
            }
        }
        for (int i = 0; i < travelVisitorTourListImage.size(); i++) {
            if (!travelVisitorTourListImage.get(i).isEmpty()) {
                s3Provider.saveFile(travelVisitorTourListImage.get(i),
                    travelFolderName + S3Provider.SEPARATOR + travelVisitorTourListImageName.get(i));
            }
        }
        for (int i = 0; i < travelVisitorOtherServiceImage.size(); i++) {
            if (!travelVisitorOtherServiceImage.get(i).isEmpty()) {
                s3Provider.saveFile(travelVisitorOtherServiceImage.get(i),
                    travelFolderName + S3Provider.SEPARATOR + travelVisitorOtherServiceImageName.get(i));
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
    private Travel findTravel(Long id,User user){
        return travelRepository.findByIdAndUser(id,user)
                .orElseThrow(()-> new NotFoundTravelException(TravelErrorCode.NOT_FOUND_TRAVEL));
    }
    private Constituency findConstituency(String constituency_name){
        return constituencyRepository.findByConstituency(constituency_name)
            .orElseThrow(()->new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
    }

    public void deleteTravel(Long id, User user) {
        Travel travel = findTravel(id,user);
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
