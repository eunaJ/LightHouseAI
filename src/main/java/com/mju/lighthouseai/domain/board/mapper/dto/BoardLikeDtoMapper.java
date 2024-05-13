package com.mju.lighthouseai.domain.board.mapper.dto;

import com.mju.lighthouseai.domain.board.dto.controller.BoardLikeControllerRequestDto;
import com.mju.lighthouseai.domain.board.dto.service.request.BoardLikeServiceRequestDto;
import com.mju.lighthouseai.domain.board.entity.Board;
import com.mju.lighthouseai.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BoardLikeDtoMapper {
    @Mapping(source = "user",target = "user")
    @Mapping(source = "board",target = "board")
    BoardLikeServiceRequestDto toBoardLikeServiceDto(
            BoardLikeControllerRequestDto controllerRequestDto, User user, Board board
    );


}


