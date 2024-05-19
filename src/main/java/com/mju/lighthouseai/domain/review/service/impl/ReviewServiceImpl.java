package com.mju.lighthouseai.domain.review.service.impl;

import com.mju.lighthouseai.domain.board.entity.Board;
import com.mju.lighthouseai.domain.board.exception.BoardErrorCode;
import com.mju.lighthouseai.domain.board.exception.NotFoundBoardException;
import com.mju.lighthouseai.domain.board.repository.BoardRepository;
import com.mju.lighthouseai.domain.review.dto.service.request.ReviewCreateServiceRequestDto;
import com.mju.lighthouseai.domain.review.dto.service.request.ReviewUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.review.dto.service.response.ReviewReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.review.entity.Review;
import com.mju.lighthouseai.domain.review.exception.ReviewErrorCode;
import com.mju.lighthouseai.domain.review.exception.NotFoundReviewException;
import com.mju.lighthouseai.domain.review.mapper.service.ReviewEntityMapper;
import com.mju.lighthouseai.domain.review.repository.ReviewRepository;
import com.mju.lighthouseai.domain.review.service.ReviewService;
import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.response.CafeReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.domain.user.entity.UserRole;
import com.mju.lighthouseai.domain.user.exception.NotFoundUserException;
import com.mju.lighthouseai.domain.user.exception.UserErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service

public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewEntityMapper reviewEntityMapper;
    private final BoardRepository boardRepository;
    private final int PAGE_SIZE = 10;
    public void createReview(Long id,ReviewCreateServiceRequestDto requestDto, User user){
        Board  board = boardRepository.findById(id)
            .orElseThrow(()->new NotFoundReviewException(ReviewErrorCode.NOT_FOUND_Review));
        Review review = reviewEntityMapper.toReview(requestDto,user,board);
        reviewRepository.save(review);
    }

    @Transactional
    public void updateReview(ReviewUpdateServiceRequestDto requestDto,Long id,User user){
        Review review = reviewRepository.findByIdAndUser(id,user)
                .orElseThrow(()->new NotFoundReviewException(ReviewErrorCode.ALREADY_EXIST_Review));
        review.updateReview(requestDto.content());
    }
    public void deleteReview(Long id,User user) {
        Review review = reviewRepository.findByIdAndUser(id,user)
            .orElseThrow(() -> new NotFoundReviewException(ReviewErrorCode.NOT_FOUND_Review));
        reviewRepository.delete(review);
    }
    public List<ReviewReadAllServiceResponseDto> readReviews(Long boardId,final Integer page){
        boardRepository.findById(boardId)
            .orElseThrow(()->new NotFoundBoardException(BoardErrorCode.NOT_FOUND_Board));
        PageRequest pageRequest = PageRequest.of(page,PAGE_SIZE, Sort.by(Direction.ASC,"id"));
        Slice<Review> reviews = reviewRepository.findAllSliceByBoardId(boardId,pageRequest);

        return reviewEntityMapper.toReviewReadAllResponseDto(reviews.getContent());
    }
    public ReviewReadAllServiceResponseDto readReview(Long id){
        Review review = reviewRepository.findById(id).
            orElseThrow(()->new NotFoundReviewException(ReviewErrorCode.NOT_FOUND_Review));
        return reviewEntityMapper.toReviewReadResponseDto(review);
    }

}
