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
@Table(name = "TB_AI_CREATE_OTHER_SERVICE")
public class AICreateOtherService
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer other_service_id;
    @Column
    private Integer star;
    @Column
    private String menu;
    @Column
    private Integer price;
    @Column
    private String title;
    @Column
    private String location;
    @Builder
    public AICreateOtherService(
            final Integer other_service_id,
            final Integer star,
            final String menu,
            final Integer price,
            final String title,
            final String location
    ) {
        this.other_service_id = other_service_id;
        this.star = star;
        this.menu = menu;
        this.price = price;
        this.title = title;
        this.location = location;
    }
}
