package com.mju.lighthouseai.domain.board.dto.controller;

import com.mju.lighthouseai.domain.user.entity.User;

public  record BoardUpdateControllerRequestDto (
    String title,
    String content,
    Boolean imageChange

) {

}
