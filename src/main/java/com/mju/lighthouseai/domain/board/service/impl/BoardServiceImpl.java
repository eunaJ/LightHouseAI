package com.mju.lighthouseai.domain.board.service.impl;

import com.mju.lighthouseai.domain.board.dto.service.request.BoardCreateServiceRequestDto;
import com.mju.lighthouseai.domain.board.dto.service.request.BoardUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.board.entity.Board;
import com.mju.lighthouseai.domain.board.exceoption.BoardErrorCode;
import com.mju.lighthouseai.domain.board.exceoption.NotFoundBoardException;
import com.mju.lighthouseai.domain.board.mapper.service.BoardEntityMapper;
import com.mju.lighthouseai.domain.board.repository.BoardRepository;
import com.mju.lighthouseai.domain.board.service.BoardService;
import com.mju.lighthouseai.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardEntityMapper boardEntityMapper;

    public void createBoard(BoardCreateServiceRequestDto requestDto) {
        Board board = boardEntityMapper.toboard(requestDto);
        boardRepository.save(board);
    }

    @Transactional
    public void updateBoard(Long id, BoardUpdateServiceRequestDto requestDto) {
        Board board = findBoard(id);
        board.updateBoard(requestDto.title(), requestDto.content(), requestDto.image_url(),
                requestDto.author(),requestDto.user());
    }

    private Board findBoard(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundBoardException(BoardErrorCode.NOT_FOUND_CAFE));
    }
}


