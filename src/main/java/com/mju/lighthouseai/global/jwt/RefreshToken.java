package com.mju.lighthouseai.global.jwt;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "RefreshToken")
public class RefreshToken {
    @Id
    private final Long id;

    private final String refreshToken;

    //@Value("${spring.jwt.refresh.expiration-period}") 참고용
    private final Long timeToLive;

    @Builder
    public RefreshToken(Long id, String refreshToken, Long timeToLive){
        this.id = id;
        this.refreshToken = refreshToken;
        this.timeToLive = timeToLive;
    }
}
