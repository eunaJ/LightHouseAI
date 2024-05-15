package com.mju.lighthouseai.domain.travel_visitor_restaurant.entity;

import com.mju.lighthouseai.domain.restaurant.entity.Restaurant;
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
@Table(name = "TB_TRAVEL_VISITOR_RESTAURANT")
public class TravelVisitorRestaurant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String image_url;

    @Column
    private String menu;

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
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "travel_id",nullable = false)
    private Travel travel;

    public TravelVisitorRestaurant(
            final String image_url,
            final String menu,
            final int price,
            final String opentime,
            final String closetime,
            final String location,
            final User user,
            final Restaurant restaurant,
            final Travel travel
    ) {
        this.image_url = image_url;
        this.menu = menu;
        this.price = price;
        this.opentime = opentime;
        this.closetime = closetime;
        this.location = location;
        this.user = user;
        this.restaurant = restaurant;
        this.travel = travel;
    }

    public void updateTravelVisitorRestaurant(
            String menu, int price, String opentime, String closetime, String location, String image_url) {
        this.menu = menu;
        this.price = price;
        this.opentime = opentime;
        this.closetime = closetime;
        this.location = location;
        this.image_url = image_url;
    }
}