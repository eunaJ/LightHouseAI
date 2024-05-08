package com.mju.lighthouseai.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.lighthouseai.domain.user.dto.kakao.KakaoUserInfoDto;
import com.mju.lighthouseai.domain.user.dto.service.response.UserLoginResponseDto;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.domain.user.entity.UserRole;
import com.mju.lighthouseai.domain.user.mapper.entity.UserEntityMapper;
import com.mju.lighthouseai.domain.user.repository.UserRepository;
import com.mju.lighthouseai.global.jwt.JwtUtil;
import com.mju.lighthouseai.global.jwt.RefreshToken;
import com.mju.lighthouseai.global.jwt.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {
    @Value("${spring.kakao.client-id}")
    private String clientId;

    @Value("${spring.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${spring.jwt.refresh.expiration-period}")
    private Long timeToLive;

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletResponse httpServletResponse;
    private final RefreshTokenRepository refreshTokenRepository;

    public UserLoginResponseDto loginWithKakao(String code) throws JsonProcessingException{
        String accessToken = getToken(code);
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);
        User user = joinKakaoUser(kakaoUserInfo);
        jwtUtil.addAccessTokenToHeader(user, httpServletResponse);
        String refresh = jwtUtil.addRefreshTokenToCookie(user, httpServletResponse);
        RefreshToken refreshToken = new RefreshToken(user.getId(), refresh, timeToLive);
        refreshTokenRepository.save(refreshToken);
        return userEntityMapper.toUserLoginResponseDto(user);
    }

    private String getToken(String code) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException{
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        String email = jsonNode.get("kakao_account").get("email").asText();
        String nickname = jsonNode.get("kakao_account")
                .get("profile").get("nickname").asText();
        String birth = jsonNode.get("kakao_account").get("birthyear").asText()+"-"
                +jsonNode.get("kakao_account").get("birthday").asText().substring(0,2)+"-"
                +jsonNode.get("kakao_account").get("birthday").asText().substring(2);
        String profile_img_url = jsonNode.get("kakao_account").get("profile").get("profile_image_url").asText();
        return new KakaoUserInfoDto(email, nickname, birth, profile_img_url);
    }

    private User joinKakaoUser(KakaoUserInfoDto kakaoUserInfoDto) {
        String email = kakaoUserInfoDto.email();
        User kakaoUser = userRepository.findByEmail(email).orElse(null);
        String uuid = UUID.randomUUID().toString();
        if(kakaoUser == null){
            kakaoUser = User.builder()
                    .email(email)
                    .password(passwordEncoder.encode(uuid))
                    .nickname(kakaoUserInfoDto.nickname())
                    .birth(kakaoUserInfoDto.birth())
                    .role(UserRole.USER)
                    .profile_img_url(kakaoUserInfoDto.profile_img_url())
                    .build();
            userRepository.save(kakaoUser);
        }
        return kakaoUser;
    }
}
