package com.schoopy.back.event.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "answer")
public class AnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AnswerId;

    @ManyToOne
    @JoinColumn(name="application_id")
    private ApplicationEntity application;

    @ManyToOne
    @JoinColumn(name="question_id")
    private QuestionEntity question;

    @Lob
    private String answer;
}
