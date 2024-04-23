package com.mju.lighthouseai.domain.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.mju.lighthouseai.domain.board.entity.Board;
@Getter
@NoArgsConstructor


public class BoardUpdateRequestDto {

    private String title;
    private String content;
    private String author;
    private String image_url;

    @Builder

    public BoardUpdateRequestDto (String title, String content, String author,String image_url) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.image_url= image_url;
    }
    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .author(author)
                .image_url(image_url)
                .build();
    }

}
