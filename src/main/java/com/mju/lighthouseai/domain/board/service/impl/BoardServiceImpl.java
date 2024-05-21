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
import com.mju.lighthouseai.domain.review.entity.Review;
import com.mju.lighthouseai.domain.user.entity.UserRole;
import com.mju.lighthouseai.domain.user.exception.NotFoundUserException;
import com.mju.lighthouseai.domain.user.exception.UserErrorCode;
import com.mju.lighthouseai.domain.user.repository.UserRepository;
import com.mju.lighthouseai.global.s3.S3Provider;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.buf.Utf8Encoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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

    private final int PAGE_SIZE = 10;

    public void createBoard(
        BoardCreateServiceRequestDto requestDto,
        User user,
        MultipartFile multipartFile
    ) throws IOException {
        String fileName;
        String fileUrl;
        String folderName = requestDto.title()+UUID.randomUUID();
        if (multipartFile.isEmpty()){
            fileUrl = null;
            Board board = boardEntityMapper.toboard(requestDto,user,fileUrl,folderName);
            boardRepository.save(board);
        }else {
            fileName = s3Provider.originalFileName(multipartFile);
            fileUrl = url + folderName + SEPARATOR + fileName;
            Board board = boardEntityMapper.toboard(requestDto,user,fileUrl,folderName);
            boardRepository.save(board);
            s3Provider.createFolder(folderName);
            fileUrl = folderName + SEPARATOR + fileName;
            s3Provider.saveFile(multipartFile,fileUrl);
        }
    }

    @Transactional
    public void updateBoard(
        Long id,
        BoardUpdateServiceRequestDto requestDto,
        User user,
        MultipartFile multipartFile
    ) throws IOException {
        Board board = boardRepository.findBoardByIdAndUser(id,user)
            .orElseThrow(()->new NotFoundCafeException(BoardErrorCode.NOT_FOUND_Board));
        if (!requestDto.imageChange()){
            board.updateBoard(requestDto.title(), requestDto.content(),board.getImage_url());
        }else{
            String imageName = s3Provider.updateImage(board.getImage_url(),
                board.getFolderName(),multipartFile);
            board.updateBoard(requestDto.title(), requestDto.content(),imageName);
        }
    }

    private Board findBoard(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundBoardException(BoardErrorCode.NOT_FOUND_Board));
    }

    public void deleteBoard(Long id, User user) {
        Board board = findBoard(id);
        System.out.println(board.getTitle());
        if (board.getImage_url()==null){
            boardRepository.delete(board);
        }else{
            String imageName = board.getImage_url().replace(url,"");
            imageName = imageName.substring(imageName.lastIndexOf("/"));
            boardRepository.delete(board);
            s3Provider.delete(board.getTitle()+imageName);
            // TODO 폴더 삭제 하기 구현하기
            // TODO 1. AWS S3 안에 있는 객체를 모두 삭제 해야 폴더 삭제 가능

        }

    }
    public List<BoardReadAllServiceResponseDto> readAllBoards(final Integer page){
        PageRequest pageRequest = PageRequest.of(page,PAGE_SIZE, Sort.by(Direction.DESC,"id"));
        Slice<Board> boards = boardRepository.findAll(pageRequest);
        return boardEntityMapper.toBoardReadAllResponseDto(boards.getContent());
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




