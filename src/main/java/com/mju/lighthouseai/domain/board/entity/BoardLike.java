package com.mju.lighthouseai.domain.board.entity;


import com.mju.lighthouseai.domain.user.entity.User;
import java.util.List;

import com.mju.lighthouseai.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_BOARDLike")


public class BoardLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;


   @Builder
    public BoardLike(
            final User user,
            final Board board
    ){
        this.user = user ;
        this.board = board ;
    }

}

