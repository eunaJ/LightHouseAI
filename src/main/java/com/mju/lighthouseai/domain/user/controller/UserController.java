package com.mju.lighthouseai.domain.user.controller;

import com.mju.lighthouseai.domain.user.dto.controller.request.UpdateUserControllerRequestDto;
import com.mju.lighthouseai.domain.user.dto.controller.request.UserLoginControllerRequestDto;
import com.mju.lighthouseai.domain.user.dto.controller.request.UserSignUpControllerRequestDto;
import com.mju.lighthouseai.domain.user.dto.service.request.*;
import com.mju.lighthouseai.domain.user.dto.service.response.UserLoginResponseDto;
import com.mju.lighthouseai.domain.user.mapper.dto.UserDtoMapper;
import com.mju.lighthouseai.domain.user.service.UserService;
import com.mju.lighthouseai.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(
        @RequestPart UserSignUpControllerRequestDto controllerRequestDto,
        @RequestPart(required=false) MultipartFile multipartFile) throws IOException {
        UserSignUpServiceRequestDto serviceRequestDto = userDtoMapper.toUserSignUpServiceRequestDto(
            controllerRequestDto);
        userService.signUp(serviceRequestDto, multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
        @RequestBody UserLoginControllerRequestDto controllerRequestDto
    ) {
        UserLoginServiceRequestDto serviceRequestDto = userDtoMapper.toUserLoginServiceRequestDto(controllerRequestDto);
        userService.login(serviceRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(
        @RequestPart UpdateUserControllerRequestDto controllerRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
        @RequestPart(required=false) MultipartFile multipartFile) throws IOException
    {
        UpdateUserServiceRequestDto serviceRequestDto = userDtoMapper.toUpdateUserServiceRequestDto(controllerRequestDto);
        userService.updateUser(userDetailsImpl.user(), serviceRequestDto,multipartFile);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(
        @RequestHeader("Cookie") String refreshToken,
        HttpServletResponse httpServletResponse
    ) {
        userService.refreshAccessToken(refreshToken, httpServletResponse);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/isnotdupemail")
    public ResponseEntity<?> isNotDupEmail(
        @RequestBody isNotDupUserEmailServiceRequestDto serviceRequestDto
    ){
        userService.isNotDupUserEmail(serviceRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/isnotdupnick")
    public ResponseEntity<?> isNotDupNickname(
        @RequestBody isNotDupUserNickServiceRequestDto serviceRequestDto
    ){
        userService.isNotDupUserNick(serviceRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/user")
    public ResponseEntity<UserLoginResponseDto> getUser(
        @RequestHeader("Authorization") String token
    ){
        UserLoginResponseDto responseDto = userService.getUser(token);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
        @RequestHeader("Authorization") String token,
        HttpServletResponse httpServletResponse
    ){
        userService.logout(token, httpServletResponse);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}