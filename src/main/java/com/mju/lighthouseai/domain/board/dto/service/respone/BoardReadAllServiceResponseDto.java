package com.mju.lighthouseai.domain.board.dto.service.respone;

import com.mju.lighthouseai.domain.user.entity.User;

public record BoardReadAllServiceResponseDto(
        String title,
        String content,
        String image_url,
        String nickname
){

}

