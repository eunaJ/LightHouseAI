package com.mju.lighthouseai.domain.user.dto.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdatePasswordControllerRequestDto(
        @JsonProperty("currentPassword")
        String currentPassword,
        String newPassword,
        String confirmNewPassword
) {
}
