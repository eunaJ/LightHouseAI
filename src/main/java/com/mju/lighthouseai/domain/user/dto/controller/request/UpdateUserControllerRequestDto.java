package com.mju.lighthouseai.domain.user.dto.controller.request;

public record UpdateUserControllerRequestDto(
        String newPassword,
        String confirmNewPassword,
        String nickname,
        String profile_img_url,
        Boolean ImageChange
){
}
