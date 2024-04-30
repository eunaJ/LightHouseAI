package com.mju.lighthouseai.domain.board.mapper.service;

import com.mju.lighthouseai.domain.board.dto.service.request.BoardCreateServiceRequestDto;
import com.mju.lighthouseai.domain.board.dto.service.respone.BoardReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.board.entity.Board;
import com.mju.lighthouseai.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)

public interface BoardEntityMapper {
    Board toboard(BoardCreateServiceRequestDto requestDto, User user);

    default String toUserName(User user){
        return user.getNickname();
    }
    @Mapping(source = "user",target = "user_name")
    BoardReadAllServiceResponseDto toBoardReadResponseDto(Board board);

    List<BoardReadAllServiceResponseDto> toBoardReadAllResponseDto(List<Board> boards);

}
