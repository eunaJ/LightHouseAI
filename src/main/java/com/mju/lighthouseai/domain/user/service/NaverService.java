package com.mju.lighthouseai.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.lighthouseai.domain.user.dto.naver.NaverUserInfoDto;
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
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class NaverService {
    @Value("${spring.naver.client-id}")
    private String clientId;

    @Value("${spring.naver.redirect-uri}")
    private String redirectUri;

    @Value("${spring.naver.client-secret}")
    private String clientSecret;

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    private final JwtUtil jwtUtil;
    private final HttpServletResponse httpServletResponse;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${spring.jwt.refresh.expiration-period}")
    private Long timeToLive;

    public UserLoginResponseDto loginWithNaver(String code) throws JsonProcessingException {
        String accessToken = getToken(code);
        NaverUserInfoDto naverUserInfo = getNaverUserInfo(accessToken);
        User user = joinNaverUser(naverUserInfo);
        jwtUtil.addAccessTokenToHeader(user, httpServletResponse);
        String refresh = jwtUtil.addRefreshTokenToCookie(user, httpServletResponse);
        RefreshToken refreshToken = new RefreshToken(user.getId(), refresh, timeToLive);
        refreshTokenRepository.save(refreshToken);
        return userEntityMapper.toUserLoginResponseDto(user);
    }

    public String getToken(String code) throws JsonProcessingException {
        ResponseEntity<String> response;
        HttpHeaders headers = new HttpHeaders();

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        if ("code".equals(code)) {
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            body.add("response_type", "code");
            body.add("client_id", clientId);
            body.add("redirect_uri", redirectUri);
            body.add("state", "lighthouseai");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(
                    "https://nid.naver.com/oauth2.0/authorize",
                    HttpMethod.POST,
                    request,
                    String.class
            );
            return response.getBody();
        }

        headers.add("Content-type", "multipart/form-data");
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("code", code);
        body.add("state", "lighthouseai");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        response = restTemplate.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                request,
                String.class
        );
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private NaverUserInfoDto getNaverUserInfo(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        // HTTP Body 생성
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        log.info("request: "+request.toString());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                request,
                String.class
        );
        String responseBody = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        JSONObject NaverUserInfoJson = new JSONObject();

        NaverUserInfoJson.put("uid", jsonNode.get("response").get("id").asText());
        NaverUserInfoJson.put("nickname", jsonNode.get("response").get("nickname").asText());
        NaverUserInfoJson.put("email", jsonNode.get("response").get("email").asText());
//        NaverUserInfoJson.put("birth", jsonNode.get("response").get("birthyear").asText()+"-"
//                +jsonNode.get("response").get("birthday").asText());
        NaverUserInfoJson.put("birth", "");
        NaverUserInfoJson.put("profile_img_url", jsonNode.get("response").get("profile_image").asText());
        return new ObjectMapper().readValue(NaverUserInfoJson.toJSONString(), NaverUserInfoDto.class);
    }

    /**
     * @breif 네이버 유저 정보를 DB에 저장하고, 해당 유저 정보를 반환합니다.
     */
    private User joinNaverUser(NaverUserInfoDto naverUserInfoDto) {
        String email = naverUserInfoDto.email();
        User naverUser = userRepository.findByEmail(email).orElse(null);
        String uuid = UUID.randomUUID().toString();
        if(naverUser == null) {
            naverUser = User.builder()
                    .email(email)
                    .password(passwordEncoder.encode(uuid))
                    .nickname(naverUserInfoDto.nickname())
                    .birth(naverUserInfoDto.birth())
                    .role(UserRole.USER)
                    .profile_img_url(naverUserInfoDto.profile_img_url())
                    .build();
            userRepository.save(naverUser);
        }
        return naverUser;
    }
}
