package com.mju.lighthouseai.domain.user.dto.controller.request;

public record UserLoginControllerRequestDto(
        String email,
        String password
) {
}
