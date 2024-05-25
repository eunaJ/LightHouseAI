package com.mju.lighthouseai.domain.like.controller;

import com.mju.lighthouseai.global.security.UserDetailsImpl;
import com.mju.lighthouseai.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/boards/{boardId}/likes")
    public ResponseEntity<?> addBoardLike(
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        likeService.addLike(boardId, userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/boards/{boardId}/likes")
    public ResponseEntity<?> deleteBoardLike(
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        likeService.deleteLike(boardId, userDetails.user());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/boards/{boardId}/likes")
    public ResponseEntity<Long> readAllLikes(
            @PathVariable Long boardId
    ){
        return ResponseEntity.status(HttpStatus.OK).
                body(likeService.getAllLikes(boardId));
    }

    @GetMapping("/boards/{boardId}/like")
    public ResponseEntity<Boolean> readIsLike(
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        return ResponseEntity.status(HttpStatus.OK).
                body(likeService.isLike(boardId, userDetails.user()));
    }
}