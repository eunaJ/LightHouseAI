package com.mju.lighthouseai.domain.user.dto.service.response;

import com.mju.lighthouseai.domain.user.entity.UserRole;

public record UserLoginResponseDto (
        Long id,
        String email,
        String nickname,
        UserRole role,
        String birth,
        String profile_img_url
){
}
