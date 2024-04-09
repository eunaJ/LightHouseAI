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
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserEntityMapper userEntityMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletResponse httpServletResponse;
    private final JwtUtil jwtUtil;

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
        return userEntityMapper.toUserLoginResponseDto(user);
    }

    @Override
    public void updatePassword(User user, UpdatePasswordServiceRequestDto serviceRequestDto) {
        if (!passwordEncoder.matches(serviceRequestDto.currentPassword(), user.getPassword())) {
            throw new NotMatchPasswordException(UserErrorCode.NOT_MATCH_PASSWORD);
        }
        if (!serviceRequestDto.newPassword().equals(serviceRequestDto.confirmNewPassword())) {
            throw new NotMatchPasswordException(UserErrorCode.NOT_MATCH_PASSWORD);
        }
        user.updatePassword(passwordEncoder.encode(serviceRequestDto.newPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateNickname(User user, UpdateNicknameServiceRequestDto serviceRequestDto) {
        if(userRepository.existsByNickname(serviceRequestDto.nickname())){
            throw new DuplicateNicknameException(UserErrorCode.DUPLICATE_NICKNAME);
        }
        user.updateNickname(serviceRequestDto.nickname());
        userRepository.save(user);
    }

    @Override
    public void updateProfileImgUrl(User user, UpdateProfileImgUrlServiceRequestDto serviceRequestDto) {
        user.updateProfile_img_url(serviceRequestDto.profile_img_url());
        userRepository.save(user);
    }
}
