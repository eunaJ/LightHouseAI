package com.mju.lighthouseai.domain.user.controller;

import com.mju.lighthouseai.domain.user.dto.controller.*;
import com.mju.lighthouseai.domain.user.dto.service.request.*;
import com.mju.lighthouseai.domain.user.mapper.dto.UserDtoMapper;
import com.mju.lighthouseai.domain.user.service.UserService;
import com.mju.lighthouseai.global.security.UserDetailsImpl;
import jakarta.validation.constraints.NotNull;
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

    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(
            @NotNull @RequestBody UpdatePasswordControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        UpdatePasswordServiceRequestDto serviceRequestDto = userDtoMapper.toUpdatePasswordServiceRequestDto(controllerRequestDto);
        userService.updatePassword(userDetailsImpl.user(), serviceRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/nickname")
    public ResponseEntity<?> updateNickname(
            @RequestBody UpdateNicknameControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        UpdateNicknameServiceRequestDto serviceRequestDto = userDtoMapper.toUpdateNicknameServiceRequestDto(controllerRequestDto);
        userService.updateNickname(userDetailsImpl.user(), serviceRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/profileimgurl")
    public ResponseEntity<?> updateProfileImgUrl(
            @RequestBody UpdateProfileImgUrlControllerRequestDto controllerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        UpdateProfileImgUrlServiceRequestDto serviceRequestDto = userDtoMapper.toUpdateProfileImgUrlServiceRequestDto(controllerRequestDto);
        userService.updateProfileImgUrl(userDetailsImpl.user(), serviceRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
