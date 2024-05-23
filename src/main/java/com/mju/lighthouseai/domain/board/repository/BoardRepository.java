package com.mju.lighthouseai.domain.board.repository;

import com.mju.lighthouseai.domain.board.entity.Board;

import com.mju.lighthouseai.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository< Board, Long> {
    Page<Board> findAll(Pageable pageable);
    Optional<Board> findBoardByIdAndUser(Long id, User user);
}
