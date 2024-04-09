package com.mju.lighthouseai.domain.user.service;

import com.mju.lighthouseai.domain.user.dto.service.request.*;
import com.mju.lighthouseai.domain.user.dto.service.response.UserLoginResponseDto;
import com.mju.lighthouseai.domain.user.entity.User;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    void signUp(UserSignUpServiceRequestDto userSignUpServiceRequestDto);

    UserLoginResponseDto login(UserLoginServiceRequestDto userLoginServiceRequestDto);


}
