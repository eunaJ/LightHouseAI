package com.mju.lighthouseai.domain.tour_list.repository;

import com.mju.lighthouseai.domain.tour_list.entity.TourList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourListRepository extends JpaRepository<TourList,Long> {
}
