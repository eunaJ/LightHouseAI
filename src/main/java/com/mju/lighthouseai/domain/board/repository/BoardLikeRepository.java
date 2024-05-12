package com.mju.lighthouseai.domain.board.repository;

import com.mju.lighthouseai.domain.board.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mju.lighthouseai.domain.board.entity.Board;
import com.mju.lighthouseai.domain.user.entity.User;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByUserIdAndBoardId(Long userId, Long boardId);
    long countByBoardId(Long boardId);

}



