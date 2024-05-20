package com.mju.lighthouseai.domain.board.service;

import com.mju.lighthouseai.domain.board.dto.service.request.BoardCreateServiceRequestDto;
import com.mju.lighthouseai.domain.board.dto.service.request.BoardUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.board.dto.service.respone.BoardReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.cafe.dto.service.response.CafeReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.user.entity.User;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {
    void createBoard (BoardCreateServiceRequestDto requestDto, User user,
        MultipartFile multipartFile) throws IOException;
    void updateBoard (Long id, BoardUpdateServiceRequestDto requestDto, User user,MultipartFile multipartFile)
        throws IOException;
    void deleteBoard(Long id, User user);

    BoardReadAllServiceResponseDto readBoard(Long id);
    List<BoardReadAllServiceResponseDto> readAllBoards(final Integer page);



}
