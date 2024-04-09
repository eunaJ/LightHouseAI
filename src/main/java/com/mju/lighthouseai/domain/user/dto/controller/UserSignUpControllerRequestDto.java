package com.mju.lighthouseai.domain.user.dto.controller;

public record UserSignUpControllerRequestDto(
        String email,
        String password,
        String confirmPassword,
        String nickname,
        String birth,
        String profile_img_url
) {
}