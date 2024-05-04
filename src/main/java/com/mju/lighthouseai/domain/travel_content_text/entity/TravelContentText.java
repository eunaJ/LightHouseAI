package com.mju.lighthouseai.domain.travel_content_text.entity;

import com.mju.lighthouseai.domain.travel_content.entity.TravelContent;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_TRAVEL_CONTENT_TEXT")
@Entity
public class TravelContentText {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "travel_content_id", nullable = false)
    private TravelContent travelContent;

    @Column
    private String text;

    public TravelContentText(
            final String text,
            final TravelContent travelContent
    ) {
        this.text = text;
        this.travelContent = travelContent;
    }
}
