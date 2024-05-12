package com.mju.lighthouseai.domain.board.service;


import com.mju.lighthouseai.domain.board.dto.service.request.BoardLikeServiceRequestDto;
import com.mju.lighthouseai.domain.user.entity.User;


public interface BoardLikeService {

    void addBoardLike (BoardLikeServiceRequestDto boardLikeServiceRequestDto, Long userId, Long boardId );
    void deleteBoardLike(BoardLikeServiceRequestDto boardLikeServiceRequestDto, Long userId, Long boardId );
    long getTotalBoardLikes (BoardLikeServiceRequestDto boardLikeServiceRequestDto, Long boardId);

}
