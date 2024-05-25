package com.mju.lighthouseai.domain.like.repository;

import com.mju.lighthouseai.domain.board.entity.Board;
import com.mju.lighthouseai.domain.like.entity.Like;
import com.mju.lighthouseai.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByBoardAndUser(Board board, User user);
    Long countByBoardId(Long boardId);
}