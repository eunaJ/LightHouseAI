package com.mju.lighthouseai.domain.restaurant.service.impl;

import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.constituency.exception.ConstituencyErrorCode;
import com.mju.lighthouseai.domain.constituency.exception.NotFoundConstituencyException;
import com.mju.lighthouseai.domain.constituency.repository.ConstituencyRepository;
import com.mju.lighthouseai.domain.restaurant.dto.service.request.RestaurantCreateServiceRequestDto;
import com.mju.lighthouseai.domain.restaurant.dto.service.request.RestaurantUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.restaurant.dto.service.response.RestaurantReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.restaurant.entity.Restaurant;
import com.mju.lighthouseai.domain.restaurant.exceoption.NotFoundRestaurantException;
import com.mju.lighthouseai.domain.restaurant.exceoption.RestaurantErrorCode;
import com.mju.lighthouseai.domain.restaurant.mapper.service.RestaurantEntityMapper;
import com.mju.lighthouseai.domain.restaurant.repository.RestaurantRepository;
import com.mju.lighthouseai.domain.restaurant.service.RestaurantService;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.domain.user.entity.UserRole;
import com.mju.lighthouseai.domain.user.exception.NotFoundUserException;
import com.mju.lighthouseai.domain.user.exception.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;
    private final ConstituencyRepository constituencyRepository;

    public void createRestaurant(RestaurantCreateServiceRequestDto requestDto, User user){
        Constituency constituency = constituencyRepository.findByConstituency(requestDto.constituency_name())
            .orElseThrow(()->new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
        Restaurant restaurant = restaurantEntityMapper.toRestaurant(requestDto,user,constituency);
        restaurantRepository.save(restaurant);
    }

    @Transactional
    public void updateRestaurant(Long id, RestaurantUpdateServiceRequestDto requestDto, User user){
        checkUserRole(user);
        Restaurant restaurant = findRestaurant(id);
        Constituency constituency = constituencyRepository.findByConstituency(requestDto.constituency_name()
        ).orElseThrow(()-> new NotFoundConstituencyException(ConstituencyErrorCode.NOT_FOUND_CONSTITUENCY));
        restaurant.updateRestaurant(requestDto.title(), requestDto.location(), requestDto.menu(), requestDto.price(),
                requestDto.opentime(), requestDto.closetime(),constituency);
    }

    public void deleteRestaurant(Long id, User user) {
        checkUserRole(user);
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundRestaurantException(RestaurantErrorCode.NOT_FOUND_Restaurant));
        restaurantRepository.delete(restaurant);
    }

    public List<RestaurantReadAllServiceResponseDto> readAllRestaurants(){
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurantEntityMapper.toRestaurantReadAllResponseDto(restaurants);
    }

    public RestaurantReadAllServiceResponseDto readRestaurant(Long id){
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(()->new NotFoundRestaurantException(RestaurantErrorCode.NOT_FOUND_Restaurant));
        return restaurantEntityMapper.toRestaurantReadResponseDto(restaurant);
    }

    private Restaurant findRestaurant(Long id){
        return restaurantRepository.findById(id)
                .orElseThrow(()-> new NotFoundRestaurantException(RestaurantErrorCode.NOT_FOUND_Restaurant));
    }

    private void checkUserRole(User user) {
        if (!(user.getRole().equals(UserRole.ADMIN))) {
            throw new NotFoundUserException(UserErrorCode.NOT_ADMIN);
        }
    }
}
