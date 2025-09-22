package com.schoopy.back.event.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="questions")
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @ManyToOne
    @JoinColumn(name="form_Id")
    @JsonBackReference
    private FormEntity form;

    private String questionText; // 질문 내용

    @Enumerated(EnumType.STRING)
    private QuestionType questionType; // 주관식(SUBJECTIVE), 객관식(MULTIPLE_CHOICE)

    @ElementCollection
    @CollectionTable(name = "question_choices", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "choice")
    private List<String> choices = new ArrayList<>();// 객관식 보기, 주관식 null

    private boolean isRequired; // 필수 응답 여부

    private boolean isMultiple; // 중복 답변 허용 여부 - - - true->체크박스(복수선택) , false->라디오버튼(단일선택)

    public enum QuestionType {
        SUBJECTIVE,       // 주관식
        MULTIPLE_CHOICE   // 객관식
    }
}

