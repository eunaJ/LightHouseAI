package com.mju.lighthouseai.domain.constituency.repository;

import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConstituencyRepository extends JpaRepository<Constituency, Long> {
    Optional<Constituency> findByConstituency(String constituency_name);
}
