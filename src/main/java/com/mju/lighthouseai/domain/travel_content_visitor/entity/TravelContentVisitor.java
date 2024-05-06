package com.mju.lighthouseai.domain.travel_content_visitor.entity;

import com.mju.lighthouseai.domain.travel_content.entity.TravelContent;
import com.mju.lighthouseai.domain.travel_visitor_cafe.entity.TravelVisitorCafe;
import com.mju.lighthouseai.domain.travel_visitor_other_service.entity.TravelVisitorOtherServiceEntity;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.entity.TravelVisitorRestaurant;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.entity.TravelVisitorShoppingMall;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.entity.TravelVisitorTourList;
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
@Table(name = "TB_TRAVEL_CONTENT_VISITOR")
@Entity
public class TravelContentVisitor extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "travel_content_id", nullable = false)
    private TravelContent travelContent;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "travel_visitor_cafe_id")
    private TravelVisitorCafe travelVisitorCafe;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "travel_visitor_restaurant_id")
    private TravelVisitorRestaurant travelVisitorRestaurant;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "travel_visitor_shoppingmall_id")
    private TravelVisitorShoppingMall travelVisitorShoppingMall;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "travel_visitor_tourlist_id")
    private TravelVisitorTourList travelVisitorTourList;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "travel_visitor_otherservice_id")
    private TravelVisitorOtherServiceEntity travelVisitorOtherService;

    @Builder
    public TravelContentVisitor(
        final TravelContent travelContent,
        final TravelVisitorCafe travelVisitorCafe,
        final TravelVisitorRestaurant travelVisitorRestaurant,
        final TravelVisitorShoppingMall travelVisitorShoppingMall,
        final TravelVisitorTourList travelVisitorTourList,
        final TravelVisitorOtherServiceEntity travelVisitorOtherService
    ) {
        this.travelContent = travelContent;
        this.travelVisitorCafe = travelVisitorCafe;
        this.travelVisitorRestaurant = travelVisitorRestaurant;
        this.travelVisitorShoppingMall = travelVisitorShoppingMall;
        this.travelVisitorTourList = travelVisitorTourList;
        this.travelVisitorOtherService = travelVisitorOtherService;
    }
}
