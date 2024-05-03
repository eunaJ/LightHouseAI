package com.mju.lighthouseai.domain.board.service.impl;

import com.mju.lighthouseai.domain.board.dto.service.request.BoardCreateServiceRequestDto;
import com.mju.lighthouseai.domain.board.dto.service.request.BoardUpdateServiceRequestDto;
import com.mju.lighthouseai.domain.board.entity.Board;
import com.mju.lighthouseai.domain.board.exception.BoardErrorCode;
import com.mju.lighthouseai.domain.board.exception.NotFoundBoardException;
import com.mju.lighthouseai.domain.board.mapper.service.BoardEntityMapper;
import com.mju.lighthouseai.domain.board.repository.BoardRepository;
import com.mju.lighthouseai.domain.board.service.BoardService;
import com.mju.lighthouseai.domain.board.dto.service.respone.BoardReadAllServiceResponseDto;
import com.mju.lighthouseai.domain.cafe.exception.NotFoundCafeException;
import com.mju.lighthouseai.domain.user.entity.UserRole;
import com.mju.lighthouseai.domain.user.exception.NotFoundUserException;
import com.mju.lighthouseai.domain.user.exception.UserErrorCode;
import com.mju.lighthouseai.domain.user.repository.UserRepository;
import com.mju.lighthouseai.global.s3.S3Provider;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mju.lighthouseai.domain.user.entity.User;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardEntityMapper boardEntityMapper;
    private final S3Provider s3Provider;

    private final String SEPARATOR = "/";
    private final String url = "https://light-house-ai.s3.ap-northeast-2.amazonaws.com/";
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public void createBoard(
        BoardCreateServiceRequestDto requestDto,
        User user,
        MultipartFile multipartFile
    ) throws IOException {
        String fileName;
        String fileUrl;
        if (multipartFile.isEmpty()){
            fileUrl = null;
            Board board = boardEntityMapper.toboard(requestDto,user,fileUrl);
            boardRepository.save(board);
        }else {
            fileName = s3Provider.originalFileName(multipartFile);
            fileUrl = url + requestDto.title() + SEPARATOR + fileName;
            Board board = boardEntityMapper.toboard(requestDto,user,fileUrl);
            boardRepository.save(board);
            s3Provider.createFolder(requestDto.title());
            fileUrl = requestDto.title() + SEPARATOR + fileName;
            s3Provider.saveFile(multipartFile,fileUrl);
        }
    }

    @Transactional
    public void updateBoard(Long id, BoardUpdateServiceRequestDto requestDto,User user) {
        checkUserRole(user);
        Board board = findBoard(id);
        board.updateBoard(requestDto.title(), requestDto.content());
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




