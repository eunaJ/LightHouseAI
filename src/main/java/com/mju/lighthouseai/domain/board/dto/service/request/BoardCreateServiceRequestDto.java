package com.mju.lighthouseai.domain.board.dto.service.request;

import com.mju.lighthouseai.domain.user.entity.User;

public  record BoardCreateServiceRequestDto (
    String title,

    String content

    )
{
}

