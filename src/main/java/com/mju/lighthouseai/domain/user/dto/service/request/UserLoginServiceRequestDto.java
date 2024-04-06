package com.mju.lighthouseai.domain.user.dto.service.request;

public record UserLoginServiceRequestDto(
        String email,
        String password
) {
}
