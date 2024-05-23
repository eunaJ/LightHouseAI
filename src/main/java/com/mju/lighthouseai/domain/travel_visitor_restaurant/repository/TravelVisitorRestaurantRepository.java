package com.mju.lighthouseai.domain.travel_visitor_restaurant.repository;

import com.mju.lighthouseai.domain.travel_visitor_other_service.entity.TravelVisitorOtherServiceEntity;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.entity.TravelVisitorRestaurant;
import com.mju.lighthouseai.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelVisitorRestaurantRepository extends JpaRepository<TravelVisitorRestaurant,Long> {
    List<TravelVisitorRestaurant> findAllByTravelId(Long id);

    Optional<TravelVisitorRestaurant> findByIdAndUser(Long id, User user);
}
