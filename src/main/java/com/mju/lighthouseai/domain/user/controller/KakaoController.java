package com.mju.lighthouseai.domain.user.controller;

import com.fasterxml.jackson.core.JacksonException;
import com.mju.lighthouseai.domain.user.dto.service.response.UserLoginResponseDto;
import com.mju.lighthouseai.domain.user.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class KakaoController {
    private final KakaoService kakaoService;

    @GetMapping("/api/v1/users/kakao/login/callback")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code)
    throws JacksonException {
        kakaoService.loginWithKakao(code);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
