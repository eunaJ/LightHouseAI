package com.mju.lighthouseai.domain.user.dto.controller.request;

public record UserSignUpControllerRequestDto(
        String email,
        String password,
        String confirmPassword,
        String nickname,
        String birth
) {
}