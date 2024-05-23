package com.mju.lighthouseai.domain.review.repository;

import com.mju.lighthouseai.domain.review.entity.Review;
import com.mju.lighthouseai.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    Optional<Review> findByIdAndUser(Long id, User user);
    Slice<Review> findAllSliceByBoardId(Long board_id, Pageable pageable);

}
