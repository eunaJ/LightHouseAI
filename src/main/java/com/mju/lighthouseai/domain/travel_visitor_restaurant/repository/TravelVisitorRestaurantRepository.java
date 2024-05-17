package com.mju.lighthouseai.domain.travel_visitor_restaurant.repository;

import com.mju.lighthouseai.domain.travel_visitor_other_service.entity.TravelVisitorOtherServiceEntity;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.entity.TravelVisitorRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelVisitorRestaurantRepository extends JpaRepository<TravelVisitorRestaurant,Long> {
    List<TravelVisitorRestaurant> findAllByTravelId(Long id);
}
