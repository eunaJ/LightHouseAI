package com.mju.lighthouseai.domain.restaurant.entity;

import com.mju.lighthouseai.domain.constituency.entity.Constituency;
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
@Table(name = "TB_RESTAURANT")
public class Restaurant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String location;

    @Column
    private String opentime;

    @Column
    private String closetime;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "constituency_id",nullable = false)
    private Constituency constituency;
    @Builder
    public Restaurant(
        final String title,
        final String location,
        final String opentime,
        final String closetime,
        final User user,
        final Constituency constituency
    ) {
        this.title = title;
        this.location = location;
        this.opentime = opentime;
        this.closetime = closetime;
        this.user = user;
        this.constituency = constituency;
    }

    public void updateRestaurant(
            String title, String location,
            String opentime, String closetime, Constituency constituency){
        this.title = title;
        this.location = location;
        this.opentime = opentime;
        this.closetime = closetime;
        this.constituency = constituency;
    }
}
