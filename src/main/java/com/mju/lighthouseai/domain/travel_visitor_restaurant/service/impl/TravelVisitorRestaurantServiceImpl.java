package com.mju.lighthouseai.domain.travel_visitor_restaurant.service.impl;

import com.mju.lighthouseai.domain.restaurant.entity.Restaurant;
import com.mju.lighthouseai.domain.restaurant.exception.NotFoundRestaurantException;
import com.mju.lighthouseai.domain.restaurant.exception.RestaurantErrorCode;
import com.mju.lighthouseai.domain.restaurant.repository.RestaurantRepository;
import com.mju.lighthouseai.domain.travel.entity.Travel;
import com.mju.lighthouseai.domain.travel.exception.NotFoundTravelException;
import com.mju.lighthouseai.domain.travel.exception.TravelErrorCode;
import com.mju.lighthouseai.domain.travel.repository.TravelRepository;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.request.TravelVisitorRestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.request.TravelVisitorRestaurantUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.dto.service.response.TravelVisitorRestaurantReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.entity.TravelVisitorRestaurant;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.exception.NotFoundTravelVisitorRestaurant;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.exception.TravelVisitorRestaurantErrorCode;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.mapper.service.TravelVisitorRestaurantEntityMapper;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.repository.TravelVisitorRestaurantRepository;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.service.TravelVisitorRestaurantService;
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
public class TravelVisitorRestaurantServiceImpl implements TravelVisitorRestaurantService {
    private final TravelVisitorRestaurantRepository travelVisitorRestaurantRepository;
    private final TravelVisitorRestaurantEntityMapper travelVisitorRestaurantEntityMapper;
    private final RestaurantRepository restaurantRepository;
    private final S3Provider s3Provider;
    private final TravelRepository travelRepository;

    private final String SEPARATOR = "/";
    private final String url = "https://light-house-ai.s3.ap-northeast-2.amazonaws.com/";
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public void createTravelVisitorRestaurant(
        Long id,
        TravelVisitorRestaurantCreateServiceRequestDto requestDto,
        User user,
        MultipartFile multipartFile
    ) throws IOException {
        String fileName;
        String fileUrl;
        Travel travel = travelRepository.findById(id)
            .orElseThrow(()->new NotFoundTravelException(TravelErrorCode.NOT_FOUND_TRAVEL));
        Restaurant restaurant = restaurantRepository.findRestaurantByTitle(requestDto.restaurant_title())
                .orElseThrow(()->new NotFoundRestaurantException(RestaurantErrorCode.NOT_FOUND_Restaurant));
        if (multipartFile == null || multipartFile.isEmpty()){
            fileUrl = null;
            TravelVisitorRestaurant travelVisitorRestaurant =
                    travelVisitorRestaurantEntityMapper.toTravelVisitorRestaurant(requestDto,fileUrl,user,restaurant,travel);
            travelVisitorRestaurantRepository.save(travelVisitorRestaurant);
        } else {
            fileName = s3Provider.originalFileName(multipartFile);
            fileUrl = url + requestDto.restaurant_title() + SEPARATOR + fileName;
            TravelVisitorRestaurant travelVisitorRestaurant =
                    travelVisitorRestaurantEntityMapper.toTravelVisitorRestaurant(requestDto,fileUrl,user,restaurant,travel);
            travelVisitorRestaurantRepository.save(travelVisitorRestaurant);
            s3Provider.createFolder(requestDto.restaurant_title());
            fileUrl = requestDto.restaurant_title() + SEPARATOR + fileName;
            s3Provider.saveFile(multipartFile,fileUrl);
        }
    }

    @Transactional
    public void updateTravelVisitorRestaurant(
        Long id,
        TravelVisitorRestaurantUpdateServiceRequestDto requestDto,
        MultipartFile multipartFile,
        User user) throws IOException{
        TravelVisitorRestaurant travelVisitorRestaurant = travelVisitorRestaurantRepository.findByIdAndUser(id,user)
            .orElseThrow(()->new NotFoundTravelVisitorRestaurant(TravelVisitorRestaurantErrorCode.NOT_FOUND_TRAVEL_VISITOR_RESTAURANT));
        Travel travel = travelRepository.findById(travelVisitorRestaurant.getTravel().getId())
            .orElseThrow(()->new NotFoundTravelException(TravelErrorCode.NOT_FOUND_TRAVEL));
        String folderName = travelVisitorRestaurant.getTravel().getFolderName();
        String fileUrl;
        Integer travel_expense = travel.getTravel_expense();
        travel_expense = travel_expense - travelVisitorRestaurant.getPrice();
        if (!requestDto.imageChange()){
            travelVisitorRestaurant.updateTravelVisitorRestaurant(
                requestDto.menu(),
                requestDto.price(),
                requestDto.content(),
                requestDto.opentime(),
                requestDto.closetime(),
                requestDto.location(),
                travelVisitorRestaurant.getImage_url());
            travel.updateExpense(travel_expense+requestDto.price());
        }else {
            fileUrl = s3Provider.updateImage(travelVisitorRestaurant.getImage_url(),folderName,multipartFile);
            travelVisitorRestaurant.updateTravelVisitorRestaurant(
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
    private TravelVisitorRestaurant findTravelVisitorRestaurant(Long id){
        return travelVisitorRestaurantRepository.findById(id)
                .orElseThrow(()-> new NotFoundTravelVisitorRestaurant(
                        TravelVisitorRestaurantErrorCode.NOT_FOUND_TRAVEL_VISITOR_RESTAURANT));
    }

    public void deleteTravelVisitorRestaurant(Long id, User user) {
        TravelVisitorRestaurant travelVisitorRestaurant = travelVisitorRestaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundTravelVisitorRestaurant(
                        TravelVisitorRestaurantErrorCode.NOT_FOUND_TRAVEL_VISITOR_RESTAURANT));
        travelVisitorRestaurantRepository.delete(travelVisitorRestaurant);
    }

    public TravelVisitorRestaurantReadAllServiceResponseDto readTravelVisitorRestaurant(Long id){
        TravelVisitorRestaurant travelVisitorRestaurant = travelVisitorRestaurantRepository.findById(id).
                orElseThrow(()->new NotFoundTravelVisitorRestaurant(
                        TravelVisitorRestaurantErrorCode.NOT_FOUND_TRAVEL_VISITOR_RESTAURANT));
        return travelVisitorRestaurantEntityMapper.toTravelVisitorRestaurantReadResponseDto(travelVisitorRestaurant);
    }

    public List<TravelVisitorRestaurantReadAllServiceResponseDto> readAllTravelVisitorRestaurants(){
        List<TravelVisitorRestaurant> travelVisitorRestaurants = travelVisitorRestaurantRepository.findAll();
        return travelVisitorRestaurantEntityMapper
                .toTravelVisitorRestaurantReadAllResponseDto(travelVisitorRestaurants);
    }

    public List<TravelVisitorRestaurantReadAllServiceResponseDto> readAllTravelVisitorRestaurantsByTravelId(Long id){
        List<TravelVisitorRestaurant> travelVisitorRestaurants = travelVisitorRestaurantRepository.findAllByTravelId(id);
        return travelVisitorRestaurantEntityMapper
                .toTravelVisitorRestaurantReadAllResponseDto(travelVisitorRestaurants);
    }
}
