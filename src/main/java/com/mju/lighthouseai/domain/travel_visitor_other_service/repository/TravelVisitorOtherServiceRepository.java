package com.mju.lighthouseai.domain.travel_visitor_other_service.repository;

import com.mju.lighthouseai.domain.travel_visitor_cafe.entity.TravelVisitorCafe;
import com.mju.lighthouseai.domain.travel_visitor_other_service.entity.TravelVisitorOtherServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelVisitorOtherServiceRepository
        extends JpaRepository<TravelVisitorOtherServiceEntity,Long> {
    List<TravelVisitorOtherServiceEntity> findAllByTravelId(Long id);
}