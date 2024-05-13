package com.mju.lighthouseai.domain.restaurant.repository;

import com.mju.lighthouseai.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    Optional<Restaurant> findRestaurantByTitle(String restaurant_title);
}
