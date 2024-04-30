package com.mju.lighthouseai.domain.board.service;

import com.mju.lighthouseai.domain.board.dto.service.request.BoardCreateServiceRequestDto;
import com.mju.lighthouseai.domain.board.dto.service.request.BoardUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.board.dto.service.respone.BoardReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.user.entity.User;
import java.util.List;

public interface BoardService {
    void createBoard(BoardCreateServiceRequestDto requestDto, User user);
    void updateBoard(Long id, BoardUpdateServiceRequestDto requestDto);
    void deleteBoard(Long id);

    List<BoardReadAllServiceResponseDto> readAllBoards();



}
