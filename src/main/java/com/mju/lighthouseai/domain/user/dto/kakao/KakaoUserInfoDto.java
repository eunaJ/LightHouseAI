package com.mju.lighthouseai.domain.user.dto.kakao;

public record KakaoUserInfoDto (
        String email,
        String nickname,
        String birth,
        String profile_img_url
) {
}
