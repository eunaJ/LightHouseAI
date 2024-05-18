package com.mju.lighthouseai.domain.travel_visitor_cafe.entity;

import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.travel.entity.Travel;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_TRAVEL_VISITOR_CAFE")
public class TravelVisitorCafe extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String menu;
    @Column
    private Integer price;
    @Column
    private String content;
    @Column
    private String opentime;

    @Column
    private String closetime;

    @Column
    private String location;

    @Column
    private String image_url;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "cafe_id", nullable = false)
    private Cafe cafe;

    @ManyToOne
    @JoinColumn(name = "travel_id",nullable = false)
    private Travel travel;

    @Builder
    public TravelVisitorCafe(
        final String menu,
        final Integer price,
        final String content,
        final String opentime,
        final String closetime,
        final String location,
        final String image_url,
        final User user,
        final Cafe cafe,
        final Travel travel
    ) {
        this.menu = menu;
        this.price = price;
        this.content = content;
        this.opentime = opentime;
        this.closetime = closetime;
        this.location = location;
        this.image_url = image_url;
        this.user = user;
        this.cafe = cafe;
        this.travel = travel;
    }

    public void updateTravelVisitorCafe(
            String menu, Integer price, String content,String opentime, String closetime, String location, String image_url) {
        this.menu = menu;
        this.price = price;
        this.content = content;
        this.opentime = opentime;
        this.closetime = closetime;
        this.location = location;
        this.image_url = image_url;
    }
}
