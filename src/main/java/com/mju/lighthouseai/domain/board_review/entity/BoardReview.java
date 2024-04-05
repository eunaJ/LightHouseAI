package com.mju.lighthouseai.domain.board_review.entity;

import com.mju.lighthouseai.domain.board.entity.Board;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_BOARD_REVIEW")
public class BoardReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String image_url;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;



    @Builder
    public BoardReview(
        final User user,
        final Board board,
        final String title,
        final String image_url
    ){
        this.user = user;
        this.board = board;
        this.title  = title;
        this.image_url = image_url;
    }
}
