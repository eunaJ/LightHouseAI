package com.mju.lighthouseai.domain.AI.entity;

import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.travel.entity.Travel;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_AI_CREATE_SHOPPINGMALL")
public class AICreateShoppingmall
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shoppingmall_id;
    @Column
    private Integer star;
    @Column
    private String title;
    @Column
    private String location;
    @Builder
    public AICreateShoppingmall(
            final Integer shoppingmall_id,
            final Integer star,
            final String title,
            final String location
    ) {
        this.shoppingmall_id = shoppingmall_id;
        this.star = star;
        this.title = title;
        this.location = location;
    }
}
