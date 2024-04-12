package com.mju.lighthouseai.domain.user.dto.controller;

public record UpdateUserControllerRequestDto(
        String newPassword,
        String confirmNewPassword,
        String nickname,
        String profile_img_url
){
}
