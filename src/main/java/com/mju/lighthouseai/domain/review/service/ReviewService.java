package com.mju.lighthouseai.domain.review.service;

import com.mju.lighthouseai.domain.board.entity.Board;
import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeCreateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.request.CafeUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.cafe.dto.service.response.CafeReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.review.dto.service.request.ReviewCreateServiceRequestDto;
import com.mju.lighthouseai.domain.review.dto.service.request.ReviewUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.review.dto.service.response.ReviewReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.user.entity.User;
import java.util.List;

public interface ReviewService {
    void createReview(Long id,ReviewCreateServiceRequestDto requestDto, User user);
    void updateReview(ReviewUpdateServiceRequestDto requestDto,Long id,User user);
    void deleteReview(Long id,User user);
    ReviewReadAllServiceResponseDto readReview(Long id);
    List<ReviewReadAllServiceResponseDto> readReviews(Long boardId,final Integer page);
}
