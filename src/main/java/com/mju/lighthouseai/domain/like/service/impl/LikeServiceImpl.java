package com.mju.lighthouseai.domain.like.service.impl;

import com.mju.lighthouseai.domain.board.entity.Board;
import com.mju.lighthouseai.domain.board.exception.NotFoundBoardException;
import com.mju.lighthouseai.domain.board.repository.BoardRepository;
import com.mju.lighthouseai.domain.like.exception.AlreadyExistLikeException;
import com.mju.lighthouseai.domain.like.exception.LikeErrorCode;
import com.mju.lighthouseai.domain.like.exception.NotFoundLikeException;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.domain.like.entity.Like;
import com.mju.lighthouseai.domain.like.repository.LikeRepository;
import com.mju.lighthouseai.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void addLike(Long boardId, User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()->new NotFoundBoardException(LikeErrorCode.NOT_FOUND_Board));
        if(likeRepository.findByBoardAndUser(board, user).isPresent()){
            throw new AlreadyExistLikeException(LikeErrorCode.ALREADY_EXIST_Like);
        }
        Like like = Like.builder()
                .user(user)
                .board(board)
                .build();
        likeRepository.save(like);
    }

    public void deleteLike(Long boardId, User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()->new NotFoundBoardException(LikeErrorCode.NOT_FOUND_Board));
        Like like = likeRepository.findByBoardAndUser(board, user)
                .orElseThrow(()->new NotFoundLikeException(LikeErrorCode.Not_Found_Like));
        likeRepository.delete(like);
    }

    public Long getAllLikes(Long boardId) {
        boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundBoardException(LikeErrorCode.NOT_FOUND_Board));
        return likeRepository.countByBoardId(boardId);
    }
}