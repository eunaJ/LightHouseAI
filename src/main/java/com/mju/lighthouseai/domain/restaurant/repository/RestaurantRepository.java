package com.mju.lighthouseai.domain.restaurant.repository;

import com.mju.lighthouseai.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
}
