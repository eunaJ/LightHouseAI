package com.mju.lighthouseai.domain.user.service.impl;

import com.mju.lighthouseai.domain.user.dto.service.UserSignUpServiceRequestDto;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.domain.user.entity.UserRole;
import com.mju.lighthouseai.domain.user.exception.AlreadyExistsEmailException;
import com.mju.lighthouseai.domain.user.exception.DuplicateNicknameException;
import com.mju.lighthouseai.domain.user.exception.NotMatchPasswordException;
import com.mju.lighthouseai.domain.user.exception.UserErrorCode;
import com.mju.lighthouseai.domain.user.mapper.entity.UserEntityMapper;
import com.mju.lighthouseai.domain.user.repository.UserRepository;
import com.mju.lighthouseai.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserEntityMapper userEntityMapper;
    private final UserRepository userRepository;

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
}
