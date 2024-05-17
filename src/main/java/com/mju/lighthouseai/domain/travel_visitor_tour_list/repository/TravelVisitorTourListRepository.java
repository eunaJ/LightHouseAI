package com.mju.lighthouseai.domain.travel_visitor_tour_list.repository;

import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.entity.TravelVisitorShoppingMall;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.entity.TravelVisitorTourList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelVisitorTourListRepository extends JpaRepository<TravelVisitorTourList, Long> {
    List<TravelVisitorTourList> findAllByTravelId(Long id);
}