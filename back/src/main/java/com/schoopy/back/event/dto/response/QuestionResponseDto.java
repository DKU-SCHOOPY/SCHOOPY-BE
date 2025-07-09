package com.schoopy.back.event.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResponseDto {
    private Long questionId;
    private String questionText;
    private String questionType;
    private boolean isRequired;
    private boolean isMultiple;
    private List<String> choices;
}