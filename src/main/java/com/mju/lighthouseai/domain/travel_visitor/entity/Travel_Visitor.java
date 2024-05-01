package com.mju.lighthouseai.domain.travel_visitor.entity;

import com.mju.lighthouseai.domain.cafe.entity.Cafe;
import com.mju.lighthouseai.domain.other_service.entity.OtherServiceEntity;
import com.mju.lighthouseai.domain.restaurant.entity.Restaurant;
import com.mju.lighthouseai.domain.shoppingmall.entity.ShoppingMall;
import com.mju.lighthouseai.domain.tour_list.entity.TourList;
import com.mju.lighthouseai.domain.travel.entity.Travel;
import com.mju.lighthouseai.global.entity.BaseEntity;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_TRAVEL_VISITOR")
@Entity
public class Travel_Visitor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "travel_id", nullable = false)
    private Travel travel;


    @ManyToOne
    @JoinColumn(name = "cafe_id", nullable = false)
    private Cafe cafe;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "shoppingmall_id", nullable = false)
    private ShoppingMall shoppingMall;

    @ManyToOne
    @JoinColumn(name = "tourlist_id", nullable = false)
    private TourList tourList;

    @ManyToOne
    @JoinColumn(name = "otherservice_id", nullable = false)
    private OtherServiceEntity otherServiceEntity;

    @Builder
    public Travel_Visitor(
        final Travel travel,
        final Cafe cafe,
        final Restaurant restaurant,
        final ShoppingMall shoppingMall,
        final TourList tourList,
        final OtherServiceEntity otherServiceEntity
    ) {
        this.travel = travel;
        this.cafe =cafe;
        this.restaurant =restaurant;
        this.shoppingMall =shoppingMall;
        this.tourList = tourList;
        this.otherServiceEntity = otherServiceEntity;
    }
}
