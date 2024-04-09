package com.mju.lighthouseai.domain.user.dto.controller;

public record UserLoginControllerRequestDto(
        String email,
        String password
) {
}
