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
    private String qr_toss_o; // form
    private String qr_toss_x; // form
    private String qr_kakaopay_o; // form
    private String qr_kakaopay_x; // form
}
