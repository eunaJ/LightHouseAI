package com.mju.lighthouseai.domain.user.controller;

import com.mju.lighthouseai.domain.user.dto.controller.*;
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

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(
            @RequestBody UserSignUpControllerRequestDto controllerRequestDto) {
        UserSignUpServiceRequestDto serviceRequestDto = userDtoMapper.toUserSignUpServiceRequestDto(
                controllerRequestDto);
        userService.signUp(serviceRequestDto);
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
            @RequestBody UpdateUserControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        UpdateUserServiceRequestDto serviceRequestDto = userDtoMapper.toUpdateUserServiceRequestDto(controllerRequestDto);
        userService.updateUser(userDetailsImpl.user(), serviceRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(
            @RequestHeader("Cookie") String refreshToken,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            HttpServletResponse httpServletResponse
    ) {
        userService.refreshAccessToken(refreshToken,
                userDetails.user(), httpServletResponse);
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
}
