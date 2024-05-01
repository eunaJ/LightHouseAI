package com.mju.lighthouseai.domain.region.repository;

import com.mju.lighthouseai.domain.region.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
}