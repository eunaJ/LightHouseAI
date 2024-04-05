package com.mju.lighthouseai.domain.other_service.entity;


import com.mju.lighthouseai.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_OTHER_SERVICE")
public class OtherService extends BaseEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String location;

    @Column
    private int price;

    @Column
    private String menu;




    @Builder
    public OtherService(
        final String title,
        final String location,
        final int price,
        final String menu
    ) {
        this.title = title;
        this.location = location;
        this.price = price;
        this.menu = menu;
    }

}

