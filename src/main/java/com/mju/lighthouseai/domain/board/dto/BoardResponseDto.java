package com.mju.lighthouseai.domain.board.dto;

import lombok.Getter;
import com.mju.lighthouseai.domain.board.entity.Board;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String image_url;

    public BoardResponseDto(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.image_url = entity.getImage_url();
    }
}
