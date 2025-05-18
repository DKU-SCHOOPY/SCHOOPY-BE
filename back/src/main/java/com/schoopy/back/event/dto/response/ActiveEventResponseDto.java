package com.schoopy.back.event.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActiveEventResponseDto {
    private Long eventCode;
    private String eventName;
    private String department;
    private String surveyStartDate;
    private String surveyEndDate;
}