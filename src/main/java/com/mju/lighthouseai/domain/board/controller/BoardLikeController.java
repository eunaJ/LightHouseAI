package com.mju.lighthouseai.domain.board.controller;

import com.mju.lighthouseai.domain.board.dto.service.request.BoardLikeServiceRequestDto;
import com.mju.lighthouseai.global.security.UserDetailsImpl;
import com.mju.lighthouseai.domain.board.service.BoardLikeService;
import com.mju.lighthouseai.domain.board.service.BoardService;
import com.mju.lighthouseai.domain.board.dto.controller.BoardLikeControllerRequestDto;
import com.mju.lighthouseai.domain.board.mapper.dto.BoardLikeDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class BoardLikeController {

    private final BoardLikeDtoMapper boardLikeDtoMapper;
    private final BoardLikeService boardLikeService;
    private final BoardService boardService;

    @PostMapping("/boards/{boardId}/like")
    public ResponseEntity<?> addBoardLike(
            @PathVariable Long boardId,
            @RequestBody BoardLikeControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        BoardLikeServiceRequestDto boardLikeServiceRequestDto =
                boardLikeDtoMapper.toBoardLikeServiceDto(controllerRequestDto,userDetails.user(),controllerRequestDto.board());
        boardLikeService.addBoardLike(boardLikeServiceRequestDto,userDetails.user().getId(),boardId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/boards/{boardId}/like")
    public ResponseEntity<?> deleteBoardLike(
            @PathVariable Long boardId,
            @RequestBody BoardLikeControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        BoardLikeServiceRequestDto boardLikeServiceRequestDto =
                boardLikeDtoMapper.toBoardLikeServiceDto(controllerRequestDto,userDetails.user(),controllerRequestDto.board());
        boardLikeService.deleteBoardLike(boardLikeServiceRequestDto,userDetails.user().getId(),boardId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }






}


