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
@Table(name="questions")
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @ManyToOne
    @JoinColumn(name="form_Id")
    private FormEntity form;

    private String questionText; // 질문 내용

    @Enumerated(EnumType.STRING)
    private QuestionType questionType; // 주관식(SUBJECTIVE), 객관식(MULTIPLE_CHOICE)

    @Lob
    private String choices; // 객관식 보기, 주관식 null

    private boolean isRequired; // 필수 응답 여부

    private boolean isMultiple; // 중복 답변 허용 여부 - - - true->체크박스(복수선택) , false->라디오버튼(단일선택)

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerEntity> answers = new ArrayList<>();

    public enum QuestionType {
        SUBJECTIVE,       // 주관식
        MULTIPLE_CHOICE   // 객관식
    }
}

