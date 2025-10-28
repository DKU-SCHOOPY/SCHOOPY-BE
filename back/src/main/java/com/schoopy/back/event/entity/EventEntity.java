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
    private Long eventCode; // event
    private String eventName; //event
    private String department; //event
    private LocalDate eventStartDate; // event
    private LocalDate eventEndDate; // event
    @Column(columnDefinition = "LONGTEXT")
    private String eventDescription; // event
    @ElementCollection
    @CollectionTable(
    name = "event_entity_event_images", // 실제 서브 테이블 이름
    joinColumns = @JoinColumn(name = "event_entity_event_code") // FK 컬럼명 (DB에 있는 FK 컬럼명 그대로)
    )
    @Column(name = "event_images") // URL 문자열이 저장된 컬럼명
    private List<String> eventImages; // event




//    @ElementCollection
//    @CollectionTable(
//            name = "event_entity_qr_code_images", // DB에 실제 존재하는 테이블명
//            joinColumns = @JoinColumn(name = "event_entity_event_code") // FK 컬럼명
//    )
//    @Column(name = "qr_code_images") // 컬럼명
//    private List<String> qrCodeImages;
}