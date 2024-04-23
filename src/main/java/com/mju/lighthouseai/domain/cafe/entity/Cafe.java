package com.mju.lighthouseai.domain.cafe.entity;

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


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_CAFE")
public class Cafe extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String location;

    @Column
    private int price;

    @Column
    private String menu;

    @Column
    private String opentime;

    @Column
    private String closetime;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Builder
    public Cafe(
        final String title,
        final String location,
        final int price,
        final String menu,
        final String opentime,
        final String closetime,
        final User user
    ){
        this.title = title;
        this.location =location;
        this.price = price;
        this.menu = menu;
        this.opentime = opentime;
        this.closetime = closetime;
        this.user = user;
    }
    public void updateCafe(
        String title, String location, int price, String menu,
        String opentime,String closetime){
        this.title = title;
        this.location = location;
        this.price = price;
        this.menu = menu;
        this.opentime = opentime;
        this.closetime = closetime;
    }
}
