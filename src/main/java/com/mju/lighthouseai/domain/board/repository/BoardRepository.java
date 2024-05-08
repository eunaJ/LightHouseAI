package com.mju.lighthouseai.domain.board.repository;

import com.mju.lighthouseai.domain.board.entity.Board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository< Board, Long> {


}
