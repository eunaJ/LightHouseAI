package com.mju.lighthouseai.domain.tour_list.repository;

import com.mju.lighthouseai.domain.tour_list.entity.TourList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TourListRepository extends JpaRepository<TourList,Long> {
    Optional<TourList> findTourListByTitle(String tourList_title);
}