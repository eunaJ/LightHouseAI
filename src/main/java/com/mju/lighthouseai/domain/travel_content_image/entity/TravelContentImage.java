package com.mju.lighthouseai.domain.travel_content_image.entity;

import com.mju.lighthouseai.domain.travel_content.entity.TravelContent;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_TRAVEL_CONTENT_IMAGE")
@Entity
public class TravelContentImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "travel_content_id", nullable = false)
    private TravelContent travelContent;

    @Column
    private String image_url;

    public TravelContentImage(
            final String image_url,
            final TravelContent travelContent
    ) {
        this.image_url = image_url;
        this.travelContent = travelContent;
    }
}
