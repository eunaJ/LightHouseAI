package com.mju.lighthouseai.sample.service;


import com.mju.lighthouseai.sample.dto.service.TestCreateServiceRequestDto;
import com.mju.lighthouseai.sample.dto.service.TestReadResponseDto;
import com.mju.lighthouseai.domain.user.entity.User;
import java.util.List;


public interface TestService {

    void create(TestCreateServiceRequestDto testRequestDto, User user);

    TestReadResponseDto get(Long id);

    List<TestReadResponseDto> getAll(int pageNumber);
}
