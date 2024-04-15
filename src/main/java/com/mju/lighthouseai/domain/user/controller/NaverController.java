package com.mju.lighthouseai.domain.user.controller;

import com.fasterxml.jackson.core.JacksonException;
import com.mju.lighthouseai.domain.user.dto.naver.NaverUserInfoDto;
import com.mju.lighthouseai.domain.user.service.NaverService;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class NaverController {
    private final NaverService naverService;

    @GetMapping("/api/v1/users/naver/login/callback")
    public ResponseEntity<?> naverLogin(@RequestParam("code") String code)
    throws JacksonException {
        if ("code".equals(code)) {
            return ResponseEntity.status(HttpStatus.OK).body(naverService.getToken(code));
        }

        NaverUserInfoDto naverUserInfo = naverService.getNaverUserInfo(naverService.getToken(code));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
