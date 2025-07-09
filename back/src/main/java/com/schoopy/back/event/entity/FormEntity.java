package com.schoopy.back.event.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="form")
public class FormEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long formId;

    @OneToOne
    @JoinColumn(name="event_id")
    private EventEntity event;

    private LocalDate surveyStartDate; // form
    private LocalDate surveyEndDate; // form
    private int maxParticipants; // form
    private int currentParticipants; // form
    //qr_결제방식_학생회비납부여부
    private String qr_toss_o; // form
    private String qr_toss_x; // form
    private String qr_kakaopay_o; // form
    private String qr_kakaopay_x; // form
    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuestionEntity> questions = new ArrayList<>();

}
