package com.schoopy.back.event.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "answer")
public class AnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @ManyToOne
    @JoinColumn(name="application_id")
    private ApplicationEntity application;

    @ManyToOne
    @JoinColumn(name="question_id")
    private QuestionEntity question;

    private String answerText;

    @ElementCollection
    @CollectionTable(name = "answer_entity_answer_list", joinColumns = @JoinColumn(name = "answer_id"))
    @Column(name = "answer_list")
    private List<String> answerList = new ArrayList<>();
}
