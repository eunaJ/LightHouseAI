package com.mju.lighthouseai.domain.board.mapper.service;

import com.mju.lighthouseai.domain.board.dto.service.request.BoardLikeServiceRequestDto;
import com.mju.lighthouseai.domain.board.dto.service.respone.BoardLikeReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.board.dto.service.respone.BoardLikeReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.board.entity.Board;
import com.mju.lighthouseai.domain.board.entity.BoardLike;


import com.mju.lighthouseai.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)

public interface BoardLikeEntityMapper {
    @Mapping(source = "user",target = "user")
    @Mapping(source = "board",target = "board")
    BoardLike toboardLike(BoardLikeServiceRequestDto boardLikeServiceRequestDto, User user, Board board);

    default Long toUserId(User user){return user.getId();}
    default Long toBoardId(Board board){
        return board.getId();
    }


}