package com.mju.lighthouseai.domain.travel.repository;

import com.mju.lighthouseai.domain.travel.entity.Travel;
import com.mju.lighthouseai.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelRepository extends JpaRepository<Travel,Long> {
    Optional<Travel> findByIdAndUser(Long id, User user);
    List<Travel> findByUser(User user);
    Page<Travel> findAll(Pageable pageable);
    void deleteAllByUser(User user);
}