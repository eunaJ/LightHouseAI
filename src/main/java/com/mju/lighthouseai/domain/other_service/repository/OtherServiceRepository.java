package com.mju.lighthouseai.domain.other_service.repository;

import com.mju.lighthouseai.domain.other_service.entity.OtherServiceEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtherServiceRepository extends JpaRepository<OtherServiceEntity,Long> {
    Optional<OtherServiceEntity> findOtherServiceEntitieByTitle(String otherService_title);

    List<OtherServiceEntity> findByConstituencyId(Long id);
}