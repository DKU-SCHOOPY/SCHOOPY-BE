package com.schoopy.back.event.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResponseDto {
    private Long questionId;
    private String questionText;
    private String answerText;
    private List<String> answerList;
}
