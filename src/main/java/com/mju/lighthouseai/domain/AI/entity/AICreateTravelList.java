package com.mju.lighthouseai.domain.AI.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_AI_CREATE_TRAVEL_LIST")
public class AICreateTravelList
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = true)
    private Integer travel_cafe_id;
    @Column(nullable = true)
    private Integer travel_restaurant_id;
    @Column(nullable = true)
    private Integer travel_shoppingmall_id;
    @Column(nullable = true)
    private Integer travel_tourlist_id;
    @Column(nullable = true)
    private Integer travel_otherservice_id;
    @Column
    private Integer travel_id;
    //null 허용
    @Column(nullable = true)
    private String closetime;
    @Column(nullable = true)
    private String image_url;
    @Column
    private String location;
    @Column(nullable = true)
    private String menu;
    @Column(nullable = true)
    private String opentime;
    @Column(nullable = true)
    private Integer price;
    @Column(nullable = true)
    private Integer cafe_id;
    @Column(nullable = true)
    private Integer restaurant_id;
    @Column(nullable = true)
    private Integer shoppingmall_id;
    @Column(nullable = true)
    private Integer tourlist_id;
    @Column(nullable = true)
    private Integer otherservice_id;
    @Column
    private Integer user_id;
    @Column(nullable = true)
    private String content;
    @Builder
    public AICreateTravelList(
            Integer travel_cafe_id,
            Integer travel_restaurant_id,
            Integer travel_shoppingmall_id,
            Integer travel_tourlist_id,
            Integer travel_otherservice_id,
            Integer travel_id,
            String closetime,
            String image_url,
            String location,
            String menu,
            String opentime,
            Integer price,
            Integer cafe_id,
            Integer restaurant_id,
            Integer shoppingmall_id,
            Integer tourlist_id,
            Integer otherservice_id,
            Integer user_id,
            String content
    )
    {
        this.travel_cafe_id = travel_cafe_id;
        this.travel_restaurant_id = travel_restaurant_id;
        this.travel_shoppingmall_id = travel_shoppingmall_id;
        this.travel_tourlist_id = travel_tourlist_id;
        this.travel_otherservice_id = travel_otherservice_id;
        this.travel_id = travel_id;
        this.closetime = closetime;
        this.image_url = image_url;
        this.location = location;
        this.menu = menu;
        this.opentime = opentime;
        this.price = price;
        this.cafe_id = cafe_id;
        this.restaurant_id = restaurant_id;
        this.shoppingmall_id = shoppingmall_id;
        this.tourlist_id = tourlist_id;
        this.otherservice_id = otherservice_id;
        this.user_id = user_id;
        this.content = content;
    }
}
