package com.mju.lighthouseai.domain.travel.entity;

import com.mju.lighthouseai.global.entity.BaseEntity;
import com.mju.lighthouseai.domain.region.entity.Region;
import com.mju.lighthouseai.domain.user.entity.User;
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
@Table(name = "TB_TRAVEL")
public class Travel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private Long travel_expense;

    @Column
    private String image_url;

    @Column(nullable = false)
    private Byte serving;

    @Column
    private Byte star;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "region_id",nullable = false)
    private Region region;

    @Builder
    public Travel(
        final String title,
        final Long travel_expense,
        final String image_url,
        final Byte serving,
        final Byte star,
        final User user,
        final Region region
    ){
        this.title = title;
        this.travel_expense = travel_expense;
        this.image_url = image_url;
        this.serving = serving;
        this.star = star;
        this.user = user;
        this.region = region;
    }
}
