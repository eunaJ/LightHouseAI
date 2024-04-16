package com.mju.lighthouseai.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.mju.lighthouseai.domain.board.service.BoardService;
import com.mju.lighthouseai.domain.board.dto.BoardResponseDto;
import com.mju.lighthouseai.domain.board.dto.BoardSaveRequestDto;
import com.mju.lighthouseai.domain.board.dto.BoardUpdateRequestDto;

@RequiredArgsConstructor
@RestController

public class BoardController {

    private final com.mju.lighthouseai.domain.board.service.BoardService BoardService;;

    @PostMapping ("/api/v1/Board")
    public Long save (@RequestBody BoardSaveRequestDto requestDto){
        return BoardService.save(requestDto);}

    @PutMapping("/api/v1/Boardt/{id}")
    public Long update(@PathVariable Long id, @RequestBody BoardUpdateRequestDto requestDto) {
        return BoardService.update(id, requestDto);
    }
    @GetMapping("/api/v1/Board/{id}")
    public BoardResponseDto findById (@PathVariable Long id)
    {

        return BoardService.findById(id);
    }

}
