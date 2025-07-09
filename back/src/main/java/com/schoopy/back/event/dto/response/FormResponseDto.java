package com.schoopy.back.event.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormResponseDto {
    private Long formId;
    private LocalDate surveyStartDate;
    private LocalDate surveyEndDate;
    private Integer maxParticipants;
    private Integer currentParticipants;
    private List<QuestionResponseDto> questions;
}
