package com.schoopy.back.event.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="event")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventCode;

    private String eventName;
    private String department;
    private LocalDate surveyStartDate;
    private LocalDate surveyEndDate;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
    private int maxParticipants;
    private int currentParticipants;
    @Column(columnDefinition = "LONGTEXT")
    private String eventDescription;
    @ElementCollection
    private List<String> eventImages;
    private String qr_toss_o;
    private String qr_toss_x;
    private String qr_kakaopay_o;
    private String qr_kakaopay_x;

//    @ElementCollection
//    @CollectionTable(
//            name = "event_entity_qr_code_images", // DB에 실제 존재하는 테이블명
//            joinColumns = @JoinColumn(name = "event_entity_event_code") // FK 컬럼명
//    )
//    @Column(name = "qr_code_images") // 컬럼명
//    private List<String> qrCodeImages;
}