package com.mju.lighthouseai.domain.board.mapper.service;

import com.mju.lighthouseai.domain.board.dto.service.request.BoardCreateServiceRequestDto;
import com.mju.lighthouseai.domain.board.entity.Board;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BoardEntityMapper {
    Board toboard(BoardCreateServiceRequestDto requestDto);
}
