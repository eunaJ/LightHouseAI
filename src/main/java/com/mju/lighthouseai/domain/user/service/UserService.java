package com.mju.lighthouseai.domain.user.service;

import com.mju.lighthouseai.domain.user.dto.service.request.*;
import com.mju.lighthouseai.domain.user.dto.service.response.UserLoginResponseDto;
import com.mju.lighthouseai.domain.user.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    void signUp(UserSignUpServiceRequestDto userSignUpServiceRequestDto, MultipartFile multipartFile) throws IOException;

    UserLoginResponseDto login(UserLoginServiceRequestDto userLoginServiceRequestDto);

    void updateUser(User user, UpdateUserServiceRequestDto updateUserServiceRequestDto,MultipartFile multipartFile)throws IOException;

    UserLoginResponseDto refreshAccessToken(String refreshToken, HttpServletResponse response);

    void isNotDupUserEmail(isNotDupUserEmailServiceRequestDto serviceRequestDto);

    void isNotDupUserNick(isNotDupUserNickServiceRequestDto serviceRequestDto);

    UserLoginResponseDto getUser(String token);

    void logout(String token, HttpServletResponse httpServletResponse);
}