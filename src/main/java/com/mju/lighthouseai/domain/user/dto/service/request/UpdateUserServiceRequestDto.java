package com.mju.lighthouseai.domain.user.dto.service.request;

public record UpdateUserServiceRequestDto(
        String newPassword,
        String confirmNewPassword,
        String nickname,
        String profile_img_url
) {
}
