package com.mju.lighthouseai.domain.travel_visitor_cafe.repository;

import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.travel_visitor_cafe.entity.TravelVisitorCafe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TravelVisitorCafeRepository extends JpaRepository<TravelVisitorCafe,Long> {
    List<TravelVisitorCafe> findAllByTravelId(Long id);
}
