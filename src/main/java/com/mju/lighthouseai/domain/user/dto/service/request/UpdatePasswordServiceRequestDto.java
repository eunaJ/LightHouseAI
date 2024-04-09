package com.mju.lighthouseai.domain.user.dto.service.request;

public record UpdatePasswordServiceRequestDto(
        String currentPassword,
        String newPassword,
        String confirmNewPassword
) {
}
