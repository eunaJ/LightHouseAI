package com.mju.lighthouseai.domain.board.dto.service.request;

import com.mju.lighthouseai.domain.board.entity.Board;
import com.mju.lighthouseai.domain.user.entity.User;

public  record BoardLikeServiceRequestDto (
        User user,
        Board board
)
{

}
