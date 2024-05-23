package com.mju.lighthouseai.domain.review.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


import com.mju.lighthouseai.domain.board.entity.Board;
import com.mju.lighthouseai.domain.review.dto.service.request.ReviewCreateServiceRequestDto;
import com.mju.lighthouseai.domain.review.dto.service.response.ReviewReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.review.entity.Review;
import com.mju.lighthouseai.domain.user.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface ReviewEntityMapper {
    @Mapping(source = "board",target = "board")
    @Mapping(source = "requestDto.content",target = "content")
    @Mapping(source = "user",target = "user")
    Review toReview(ReviewCreateServiceRequestDto requestDto, User user, Board board);
    default String toUserName(User user){
        return user.getNickname();
    }
    @Mapping(source = "user",target = "writer")
    ReviewReadAllServiceResponseDto toReviewReadResponseDto(Review review);

    List<ReviewReadAllServiceResponseDto> toReviewReadAllResponseDto(List<Review> reviews);
}
