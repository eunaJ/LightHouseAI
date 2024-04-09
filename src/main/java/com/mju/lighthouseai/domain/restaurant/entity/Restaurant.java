package com.mju.lighthouseai.domain.restaurant.entity;

import com.mju.lighthouseai.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RESTAURANT")
public class Restaurant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String location;

    @Column
    private String menu;

    @Column
    private int price;

    @Column(nullable = false)
    private String opentime;

    @Column(nullable = false)
    private String closetime;

    @Builder
    public Restaurant(
        final String title,
        final String location,
        final String menu,
        final int price,
        final String opentime,
        final String closetime
    ) {
        this.title = title;
        this.location = location;
        this.menu = menu;
        this.price = price;
        this.opentime = opentime;
        this.closetime = closetime;
    }
}
