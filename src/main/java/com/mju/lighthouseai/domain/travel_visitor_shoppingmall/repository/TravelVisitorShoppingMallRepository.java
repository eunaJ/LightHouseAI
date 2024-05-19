package com.mju.lighthouseai.domain.travel_visitor_shoppingmall.repository;

import com.mju.lighthouseai.domain.travel_visitor_restaurant.entity.TravelVisitorRestaurant;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.entity.TravelVisitorShoppingMall;
import com.mju.lighthouseai.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelVisitorShoppingMallRepository extends JpaRepository<TravelVisitorShoppingMall,Long> {
    List<TravelVisitorShoppingMall> findAllByTravelId(Long id);
    Optional<TravelVisitorShoppingMall> findByIdAndUser(Long id, User user);
}