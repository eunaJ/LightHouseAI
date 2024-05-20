package com.mju.lighthouseai.domain.cafe.repository;

import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CafeRepository extends JpaRepository<Cafe,Long> {
    Optional<Cafe> findCafeByTitle(String cafe_title);

    List<Cafe> findByConstituencyId(Long id);
}
