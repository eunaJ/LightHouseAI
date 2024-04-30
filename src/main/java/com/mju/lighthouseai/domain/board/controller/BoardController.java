package com.mju.lighthouseai.domain.board.controller;

import com.mju.lighthouseai.domain.board.dto.controller.BoardCreateControllerRequestDto;
import com.mju.lighthouseai.domain.board.dto.controller.BoardUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.board.dto.service.request.BoardCreateServiceRequestDto;
import com.mju.lighthouseai.domain.board.dto.service.request.BoardUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.board.mapper.dto.BoardDtoMapper;
import com.mju.lighthouseai.domain.board.service.BoardService;
import com.mju.lighthouseai.global.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<?> createBoard(
            @RequestBody BoardCreateControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        BoardCreateServiceRequestDto serviceRequestDto =
                boardDtoMapper.toBoardCreateServiceDto(controllerRequestDto);
        boardService.createBoard(serviceRequestDto,userDetails.user());
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

    @DeleteMapping("/boards/{bordId}")
    public ResponseEntity<?> deleteBoard(
            @PathVariable Long boardId
    ){
        boardService.deleteBoard(boardId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/boards")
    public ResponseEntity<?> readAllBoards(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(boardService.readAllBoards());
    }
}


