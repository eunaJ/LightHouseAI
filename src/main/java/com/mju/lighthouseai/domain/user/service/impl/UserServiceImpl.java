package com.mju.lighthouseai.domain.user.service.impl;

import com.mju.lighthouseai.domain.user.dto.service.request.*;
import com.mju.lighthouseai.domain.user.dto.service.response.UserLoginResponseDto;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.domain.user.entity.UserRole;
import com.mju.lighthouseai.domain.user.exception.*;
import com.mju.lighthouseai.domain.user.mapper.entity.UserEntityMapper;
import com.mju.lighthouseai.domain.user.repository.UserRepository;
import com.mju.lighthouseai.domain.user.service.UserService;
import com.mju.lighthouseai.global.jwt.JwtUtil;
import com.mju.lighthouseai.global.jwt.RefreshToken;
import com.mju.lighthouseai.global.jwt.RefreshTokenRepository;
import com.mju.lighthouseai.global.jwt.exception.ExpiredJwtAccessTokenException;
import com.mju.lighthouseai.global.jwt.exception.ExpiredJwtRefreshTokenException;
import com.mju.lighthouseai.global.jwt.exception.FailedJwtTokenException;
import com.mju.lighthouseai.global.jwt.exception.JwtErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserEntityMapper userEntityMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletResponse httpServletResponse;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${spring.jwt.refresh.expiration-period}")
    private Long timeToLive;

    @Override
    public void signUp(final UserSignUpServiceRequestDto serviceRequestDto) {
        // 이메일 중복 체크
        if (userRepository.existsByEmail(serviceRequestDto.email())) {
            throw new AlreadyExistsEmailException(UserErrorCode.ALREADY_EXIST_EMAIL);
        }
        // 닉네임 중복 체크
        if(userRepository.existsByNickname(serviceRequestDto.nickname())) {
            throw new DuplicateNicknameException(UserErrorCode.DUPLICATE_NICKNAME);
        }
        // 비번 확인 체크
        if(!serviceRequestDto.password().equals(serviceRequestDto.confirmPassword())) {
            throw new NotMatchPasswordException(UserErrorCode.NOT_MATCH_PASSWORD);
        }
        User user = userEntityMapper.toUser(serviceRequestDto, UserRole.USER);
        userRepository.save(user);
    }

    @Override
    public UserLoginResponseDto login(final UserLoginServiceRequestDto userLoginServiceRequestDto) {
        User user = userRepository.findByEmail(userLoginServiceRequestDto.email())
                .orElseThrow(() -> new NotFoundUserException(UserErrorCode.NOT_FOUND_USER));
        if(!passwordEncoder.matches(userLoginServiceRequestDto.password(),user.getPassword())){
            throw new NotMatchPasswordException(UserErrorCode.NOT_MATCH_PASSWORD);
        }
        jwtUtil.addAccessTokenToHeader(user, httpServletResponse);
        String refresh = jwtUtil.addRefreshTokenToCookie(user, httpServletResponse);
        RefreshToken refreshToken = new RefreshToken(user.getId(), refresh, timeToLive);
        refreshTokenRepository.save(refreshToken);
        return userEntityMapper.toUserLoginResponseDto(user);
    }

    @Override
    public void updateUser(User user, UpdateUserServiceRequestDto serviceRequestDto) {
        // 비밀번호
        if(!Objects.equals(serviceRequestDto.newPassword(), "")){
            if (passwordEncoder.matches(serviceRequestDto.newPassword(), user.getPassword())) {
                throw new NotMatchPasswordException(UserErrorCode.MATCH_CURRENT_PASSWORD);
            }
            if (!serviceRequestDto.newPassword().equals(serviceRequestDto.confirmNewPassword())) {
                throw new NotMatchPasswordException(UserErrorCode.NOT_MATCH_PASSWORD);
            }
            user.updatePassword(passwordEncoder.encode(serviceRequestDto.newPassword()));
        }
        // 닉네임
        if(!Objects.equals(serviceRequestDto.nickname(), "")){
            if(userRepository.existsByNickname(serviceRequestDto.nickname())){
                throw new DuplicateNicknameException(UserErrorCode.DUPLICATE_NICKNAME);
            }
            user.updateNickname(serviceRequestDto.nickname());
        }
        // 프로필 이미지
        if(!Objects.equals(serviceRequestDto.profile_img_url(), "")) {
            user.updateProfile_img_url(serviceRequestDto.profile_img_url());
        }
        userRepository.save(user);
    }

    @Override
    public UserLoginResponseDto refreshAccessToken(final String refreshTokenCookie,
                                                   final HttpServletResponse response) {
        String refresh = refreshTokenCookie.substring(13);
        // 애초에 다르면 검증에 오류
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(refresh)
                .orElseThrow(() -> new ExpiredJwtRefreshTokenException(JwtErrorCode.EXPIRED_JWT_REFRESH_TOKEN));
        if (!jwtUtil.validateRefreshToken(refreshToken.getRefreshToken())) {
            throw new FailedJwtTokenException(JwtErrorCode.FAILED_JWT_TOKEN);
        }
        User user = userRepository.findById(refreshToken.getId()).orElseThrow(()->new NotFoundUserException(UserErrorCode.NOT_FOUND_USER));
        jwtUtil.addAccessTokenToHeader(user, httpServletResponse);
        String newToken = jwtUtil.addRefreshTokenToCookie(user, response);
        refreshTokenRepository.deleteById(String.valueOf(user.getId()));
        refreshTokenRepository.save(new RefreshToken(user.getId(), newToken, timeToLive));
        return userEntityMapper.toUserLoginResponseDto(user);
    }

    @Override
    public void isNotDupUserEmail(isNotDupUserEmailServiceRequestDto serviceRequestDto) {
        if (userRepository.existsByEmail(serviceRequestDto.email())) {
            log.info("중복된 이메일입니다.");
            throw new AlreadyExistsEmailException(UserErrorCode.ALREADY_EXIST_EMAIL);
        }
    }

    @Override
    public void isNotDupUserNick(isNotDupUserNickServiceRequestDto serviceRequestDto){
        if(userRepository.existsByNickname(serviceRequestDto.nickname())) {
            log.info("중복된 닉네임입니다.");
            throw new DuplicateNicknameException(UserErrorCode.DUPLICATE_NICKNAME);
        }
    }

    @Override
    public UserLoginResponseDto getUser(final String token){
        if (jwtUtil.isExpiredAccessToken(token)) {
            throw new ExpiredJwtAccessTokenException(JwtErrorCode.EXPIRED_JWT_ACCESS_TOKEN);
        }
        String email = jwtUtil.getUserInfoFromToken(token).getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundUserException(UserErrorCode.NOT_FOUND_USER));
        return userEntityMapper.toUserLoginResponseDto(user);
    }

    @Override
    public void logout(final String token){
        if (jwtUtil.isExpiredAccessToken(token)) {
            throw new ExpiredJwtAccessTokenException(JwtErrorCode.EXPIRED_JWT_ACCESS_TOKEN);
        }
        String email = jwtUtil.getUserInfoFromToken(token).getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundUserException(UserErrorCode.NOT_FOUND_USER));
        refreshTokenRepository.deleteById(String.valueOf(user.getId()));
    }
}
