package com.mju.lighthouseai.domain.cafe.repository;

import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeRepository extends JpaRepository<Cafe,Long> {
}
