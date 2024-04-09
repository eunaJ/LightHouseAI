package com.mju.lighthouseai.domain.user.service;

import com.mju.lighthouseai.domain.user.dto.service.request.*;
import com.mju.lighthouseai.domain.user.dto.service.response.UserLoginResponseDto;
import com.mju.lighthouseai.domain.user.entity.User;

public interface UserService {
    void signUp(UserSignUpServiceRequestDto userSignUpServiceRequestDto);

    UserLoginResponseDto login(UserLoginServiceRequestDto userLoginServiceRequestDto);

    void updatePassword(User user, UpdatePasswordServiceRequestDto updatePasswordServiceRequestDto);

    void updateNickname(User user, UpdateNicknameServiceRequestDto updateNicknameServiceRequestDto);

    void updateProfileImgUrl(User user, UpdateProfileImgUrlServiceRequestDto updateProfileImgUrlServiceRequestDto);
}
