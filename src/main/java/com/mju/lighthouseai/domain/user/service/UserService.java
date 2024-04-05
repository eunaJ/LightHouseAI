package com.mju.lighthouseai.domain.user.service;

import com.mju.lighthouseai.domain.user.dto.service.UserSignUpServiceRequestDto;

public interface UserService {
    void signUp(UserSignUpServiceRequestDto userSignUpServiceRequestDto);

}
