package com.mju.lighthouseai.domain.board.service.impl;

import com.mju.lighthouseai.domain.board.dto.service.request.BoardLikeServiceRequestDto;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.domain.user.repository.UserRepository;

import com.mju.lighthouseai.domain.board.entity.BoardLike;
import com.mju.lighthouseai.domain.board.mapper.service.BoardLikeEntityMapper;
import com.mju.lighthouseai.domain.board.repository.BoardLikeRepository;
import com.mju.lighthouseai.domain.board.service.BoardLikeService;
import com.mju.lighthouseai.global.s3.S3Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@RequiredArgsConstructor
@Service
public class BoardLikeServiceImpl implements BoardLikeService {

    private final BoardLikeRepository boardLikeRepository;
    private final BoardLikeEntityMapper boardLikeEntityMapper;
    private final S3Provider s3Provider;

    private final String SEPARATOR = "/";
    private final String url = "https://light-house-ai.s3.ap-northeast-2.amazonaws.com/";
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;


    @Transactional
    public void addBoardLike(
            BoardLikeServiceRequestDto boardLikeServiceRequestDto,
            Long userId,
            Long boardId
    ) {
        userId = boardLikeServiceRequestDto.user().getId();
        boardId = boardLikeServiceRequestDto.board().getId();

        if (boardLikeRepository.findByUserIdAndBoardId(userId, boardId).isPresent()) {
            deleteBoardLike(boardLikeServiceRequestDto, userId, boardId);
        }

        BoardLike boardLike = boardLikeEntityMapper.toboardLike(boardLikeServiceRequestDto, boardLikeServiceRequestDto.user(), boardLikeServiceRequestDto.board());
        boardLikeRepository.save(boardLike);
    }

    public void deleteBoardLike(
            BoardLikeServiceRequestDto boardLikeServiceRequestDto,
            Long userId,
            Long boardId
    ) {
        // userId = boardLikeServiceRequestDto.user().getId();
       // boardId = boardLikeServiceRequestDto.board().getId();
        BoardLike boardLike = boardLikeEntityMapper.toboardLike(boardLikeServiceRequestDto, boardLikeServiceRequestDto.user(), boardLikeServiceRequestDto.board());
        boardLikeRepository.delete(boardLike);
    }

    public long getTotalBoardLikes(
            BoardLikeServiceRequestDto boardLikeServiceRequestDto,
            Long boardId
    ) {
        boardId = boardLikeServiceRequestDto.board().getId();
         return boardLikeRepository.countByBoardId(boardId);
    }





}







