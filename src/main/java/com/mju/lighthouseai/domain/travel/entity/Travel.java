package com.mju.lighthouseai.domain.travel.entity;

import com.mju.lighthouseai.domain.constituency.entity.Constituency;
import com.mju.lighthouseai.domain.travel_visitor_cafe.entity.TravelVisitorCafe;
import com.mju.lighthouseai.domain.travel_visitor_other_service.entity.TravelVisitorOtherServiceEntity;
import com.mju.lighthouseai.domain.travel_visitor_restaurant.entity.TravelVisitorRestaurant;
import com.mju.lighthouseai.domain.travel_visitor_shoppingmall.entity.TravelVisitorShoppingMall;
import com.mju.lighthouseai.domain.travel_visitor_tour_list.entity.TravelVisitorTourList;
import com.mju.lighthouseai.global.entity.BaseEntity;
import com.mju.lighthouseai.domain.region.entity.Region;
import com.mju.lighthouseai.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_TRAVEL")
public class Travel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private Integer travel_expense;

    @Column
    private String image_url;

    @Column(nullable = false)
    private Byte serving;

    @Column
    private Byte star;

    @Column
    private String folderName;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "constituency_id",nullable = false)
    private Constituency constituency;

    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<TravelVisitorCafe> travelVisitorCafes = new ArrayList<>();

   @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<TravelVisitorRestaurant> travelVisitorRestaurants = new ArrayList<>();

    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<TravelVisitorShoppingMall> travelVisitorShoppingMalls = new ArrayList<>();

    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<TravelVisitorTourList> travelVisitorTourLists = new ArrayList<>();

      @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<TravelVisitorOtherServiceEntity> travelVisitorOtherServiceEntities = new ArrayList<>();

    @Builder
    public Travel(
        final String title,
        final Integer travel_expense,
        final String image_url,
        final Byte serving,
        final Byte star,
        final String folderName,
        final User user,
        final Constituency constituency
    ){
        this.title = title;
        this.travel_expense = travel_expense;
        this.image_url = image_url;
        this.serving = 4;
        this.star = star;
        this.folderName=folderName;
        this.user = user;
        this.constituency = constituency;
    }
    public void UpdateTravel(
        String title,
        Integer travel_expense,
        String image_url,
        Byte serving,
        Byte star,
        Constituency constituency
    ){
        this.title = title;
        this.travel_expense = travel_expense;
        this.image_url = image_url;
        this.serving = serving;
        this.star = star;
        this.constituency = constituency;
    }
}
