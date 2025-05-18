package com.schoopy.back.event.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
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
    @ElementCollection
    private List<String> QrCodeImages;
}