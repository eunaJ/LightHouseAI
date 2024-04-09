package com.mju.lighthouseai.domain.user.dto.service.request;

public record UserSignUpServiceRequestDto(
        String email,
        String password,
        String confirmPassword,
        String nickname,
        String birth,
        String profile_img_url
) {
}