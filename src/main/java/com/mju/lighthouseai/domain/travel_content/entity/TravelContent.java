package com.mju.lighthouseai.domain.travel_content.entity;

import com.mju.lighthouseai.domain.travel.entity.Travel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_TRAVEL_CONTENT")
@Entity
public class TravelContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "travel_id", nullable = false)
    private Travel travel;

    public TravelContent(
            final Travel travel
    ) {
        this.travel = travel;
    }
}
