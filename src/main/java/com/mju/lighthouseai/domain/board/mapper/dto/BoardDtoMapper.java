package com.mju.lighthouseai.domain.board.mapper.dto;

import com.mju.lighthouseai.domain.board.dto.controller.BoardCreateControllerRequestDto;
import com.mju.lighthouseai.domain.board.dto.service.request.BoardCreateServiceRequestDto;
import com.mju.lighthouseai.domain.board.dto.service.request.BoardUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.board.dto.controller.BoardUpdateControllerRequestDto;
import com.mju.lighthouseai.domain.user.entity.User;
import org.mapstruct.Mapper;


import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BoardDtoMapper {

    BoardCreateServiceRequestDto toBoardCreateServiceDto(
            BoardCreateControllerRequestDto controllerRequestDto
    );

    BoardUpdateServiceRequestDto toBoardUpdateServiceDto(
        BoardUpdateControllerRequestDto controllerRequestDto
    );
}


