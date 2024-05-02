package com.mju.lighthouseai.domain.board.dto.controller;

public record BoardCreateControllerRequestDto (
        String title,

        String content,

        String image_url

){
}
