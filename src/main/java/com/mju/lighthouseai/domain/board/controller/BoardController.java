package com.mju.lighthouseai.domain.board.controller;

import com.mju.lighthouseai.domain.board.dto.controller.BoardCreateControllerRequestDto;
import com.mju.lighthouseai.domain.board.dto.controller.BoardUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.board.dto.service.request.BoardCreateServiceRequestDto;
import com.mju.lighthouseai.domain.board.dto.service.request.BoardUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.board.mapper.dto.BoardDtoMapper;
import com.mju.lighthouseai.domain.board.service.BoardService;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class BoardController {

    private final BoardDtoMapper boardDtoMapper;
    private final BoardService boardService;

    @PostMapping("/boards/create")
    public ResponseEntity<?> createCafe(
            @RequestBody BoardCreateControllerRequestDto controllerRequestDto
    ){
        BoardCreateServiceRequestDto serviceRequestDto =
                boardDtoMapper.toBoardCreateServiceDto(controllerRequestDto);
        boardService.createBoard(serviceRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("/boards/{boardId}")
    public ResponseEntity<?> updateBoard(
            @PathVariable Long boardId,
            @RequestBody BoardUpdateControllerRequestDto controllerRequestDto
    ){
        BoardUpdateServiceRequestDto serviceRequestDto =
                boardDtoMapper.toBoardUpdateServiceDto(controllerRequestDto);
        boardService.updateBoard(boardId,serviceRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

