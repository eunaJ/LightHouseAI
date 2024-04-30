package com.mju.lighthouseai.domain.board.service;

import com.mju.lighthouseai.domain.board.dto.service.request.BoardCreateServiceRequestDto;
import com.mju.lighthouseai.domain.board.dto.service.request.BoardUpdateServiceRequestDto;

public interface BoardService {
    void createBoard(BoardCreateServiceRequestDto requestDto);
    void updateBoard(Long id, BoardUpdateServiceRequestDto requestDto);
}
