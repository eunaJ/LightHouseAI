package com.mju.lighthouseai.domain.user.dto.naver;

public record NaverUserInfoDto (
    String uid,
    String email,
    String nickname,
    String birth,
    String profile_img_url
) {
}
