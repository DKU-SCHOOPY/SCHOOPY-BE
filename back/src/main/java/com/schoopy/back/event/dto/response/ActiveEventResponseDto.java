package com.schoopy.back.event.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActiveEventResponseDto {
    private Long eventCode;
    private String eventName;
    private String department;

    private Long FormId;
    private LocalDate surveyStartDate;
    private LocalDate surveyEndDate;
    private int maxParticipants;
    private int currentParticipants;
}