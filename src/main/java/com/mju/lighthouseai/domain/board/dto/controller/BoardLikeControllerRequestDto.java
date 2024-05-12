package com.mju.lighthouseai.domain.board.dto.controller;

import com.mju.lighthouseai.domain.board.entity.Board;
import com.mju.lighthouseai.domain.user.entity.User;

public record  BoardLikeControllerRequestDto(
        User user,
        Board board

) {

}
