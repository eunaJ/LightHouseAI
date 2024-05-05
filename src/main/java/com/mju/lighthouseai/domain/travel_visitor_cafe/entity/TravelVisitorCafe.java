package com.mju.lighthouseai.domain.travel_visitor_cafe.entity;

import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_TRAVEL_VISITOR_CAFE")
public class TravelVisitorCafe extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String image_url;

    @Column
    private int cost;

    @Column
    private String opentime;

    @Column
    private String closetime;

    @Column
    private String location;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "cafe_id", nullable = false)
    private Cafe cafe;

    public TravelVisitorCafe(
            final String image_url,
            final int cost,
            final String opentime,
            final String closetime,
            final String location,
            final User user,
            final Cafe cafe
    ) {
        this.image_url = image_url;
        this.cost = cost;
        this.opentime = opentime;
        this.closetime = closetime;
        this.location = location;
        this.user = user;
        this.cafe = cafe;
    }

    public void updateTravelVisitorCafe(
            int cost, String opentime, String closetime, String location, String image_url) {
        this.cost = cost;
        this.opentime = opentime;
        this.closetime = closetime;
        this.location = location;
        this.image_url = image_url;
    }
}
