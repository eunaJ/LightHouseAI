package com.mju.lighthouseai.domain.board.service.impl;

import com.mju.lighthouseai.domain.board.dto.service.request.BoardCreateServiceRequestDto;
import com.mju.lighthouseai.domain.board.dto.service.request.BoardUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.board.entity.Board;
import com.mju.lighthouseai.domain.board.exceoption.BoardErrorCode;
import com.mju.lighthouseai.domain.board.exceoption.NotFoundBoardException;
import com.mju.lighthouseai.domain.board.mapper.service.BoardEntityMapper;
import com.mju.lighthouseai.domain.board.repository.BoardRepository;
import com.mju.lighthouseai.domain.board.service.BoardService;
import com.mju.lighthouseai.domain.board.dto.service.respone.BoardReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.cafe.dto.service.response.CafeReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.cafe.exception.CafeErrorCode;
import com.mju.lighthouseai.domain.cafe.exception.NotFoundCafeException;
import com.mju.lighthouseai.domain.user.entity.UserRole;
import com.mju.lighthouseai.domain.user.exception.NotFoundUserException;
import com.mju.lighthouseai.domain.user.exception.UserErrorCode;
import com.mju.lighthouseai.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mju.lighthouseai.domain.user.entity.User;

import java.util.List;



@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardEntityMapper boardEntityMapper;
    private final UserRepository userRepository;

    public void createBoard(BoardCreateServiceRequestDto requestDto,User user) {
        Board board = boardEntityMapper.toboard(requestDto,user);
        boardRepository.save(board);
    }

    @Transactional
    public void updateBoard(Long id, BoardUpdateServiceRequestDto requestDto,User user) {
        checkUserRole(user);
        Board board = findBoard(id);
        board.updateBoard(requestDto.title(), requestDto.content(), requestDto.image_url());
    }

    private Board findBoard(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundBoardException(BoardErrorCode.NOT_FOUND_Board));
    }

    public void deleteBoard(Long id, User user) {
        checkUserRole(user);
        Board delete_board = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundBoardException(BoardErrorCode.NOT_FOUND_Board));
        boardRepository.delete(delete_board);
    }
    public List<BoardReadAllServiceResponseDto> readAllBoards(){
        List<Board> boards = boardRepository.findAll();
        return boardEntityMapper.toBoardReadAllResponseDto(boards);
    }

    public BoardReadAllServiceResponseDto readBoard(Long id){
        Board board = boardRepository.findById(id).
                orElseThrow(()->new NotFoundCafeException(BoardErrorCode.NOT_FOUND_Board));
        return boardEntityMapper.toBoardReadResponseDto(board);
    }
    private void checkUserRole(User user) {
        if (!(user.getRole().equals(UserRole.ADMIN))) {
            throw new NotFoundUserException(UserErrorCode.NOT_ADMIN);
        }
    }


}




