package com.mju.lighthouseai.domain.board.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mju.lighthouseai.domain.board.entity.Board;
import com.mju.lighthouseai.domain.board.dto.BoardResponseDto;
import com.mju.lighthouseai.domain.board.dto.BoardSaveRequestDto;
import com.mju.lighthouseai.domain.board.dto.BoardUpdateRequestDto;
import com.mju.lighthouseai.domain.board.repository.BoardRepository;
import com.mju.lighthouseai.domain.board.controller.BoardController;


@RequiredArgsConstructor
@Service


public class BoardService {
    private final BoardRepository boardRepository ;
    @Transactional
    public Long save(BoardSaveRequestDto requestDto) {
        return boardRepository.save(requestDto.toEntity()).getId();

    }
    public Long update (Long id, BoardUpdateRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ 1d));
        board.update(requestDto.getTitle(), requestDto.getContent ());
        return id;
    }
    public BoardResponseDto findById (Long id) {
        Board entity = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. d=" + 1d));

        return new BoardResponseDto(entity);
    }

}
