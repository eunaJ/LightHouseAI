package com.mju.lighthouseai.domain.travel_visitor_shoppingmall.entity;

import com.mju.lighthouseai.domain.shoppingmall.entity.ShoppingMall;
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
    private int price;

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

    public TravelVisitorShoppingMall(
            final String image_url,
            final int price,
            final String opentime,
            final String closetime,
            final String location,
            final User user,
            final ShoppingMall shoppingMall
    ) {
        this.image_url = image_url;
        this.price = price;
        this.opentime = opentime;
        this.closetime = closetime;
        this.location = location;
        this.user = user;
        this.shoppingMall = shoppingMall;
    }
}