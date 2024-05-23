package com.mju.lighthouseai.domain.travel_visitor_shoppingmall.entity;

import com.mju.lighthouseai.domain.shoppingmall.entity.ShoppingMall;
import com.mju.lighthouseai.domain.travel.entity.Travel;
import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_TRAVEL_VISITOR_SHOPPINGMALL")
public class TravelVisitorShoppingMall extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String image_url;

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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "shoppingmall_id", nullable = false)
    private ShoppingMall shoppingMall;

    @ManyToOne
    @JoinColumn(name = "travel_id",nullable = false)
    private Travel travel;
    public TravelVisitorShoppingMall(
            final String image_url,
            final Integer price,
            final String content,
            final String opentime,
            final String closetime,
            final String location,
            final User user,
            final ShoppingMall shoppingMall,
            final Travel travel
    ) {
        this.image_url = image_url;
        this.price = price;
        this.content = content;
        this.opentime = opentime;
        this.closetime = closetime;
        this.location = location;
        this.user = user;
        this.shoppingMall = shoppingMall;
        this.travel = travel;
    }

    public void updateTravelVisitorShoppingMall(
            Integer price,String content ,String opentime, String closetime, String location, String image_url) {
        this.price = price;
        this.content = content;
        this.opentime = opentime;
        this.closetime = closetime;
        this.location = location;
        this.image_url = image_url;
    }
}