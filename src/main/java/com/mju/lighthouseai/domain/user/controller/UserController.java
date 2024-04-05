package com.mju.lighthouseai.domain.user.controller;

import com.mju.lighthouseai.domain.user.dto.controller.UserSignUpControllerRequestDto;
import com.mju.lighthouseai.domain.user.dto.service.UserSignUpServiceRequestDto;
import com.mju.lighthouseai.domain.user.mapper.dto.UserDtoMapper;
import com.mju.lighthouseai.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
