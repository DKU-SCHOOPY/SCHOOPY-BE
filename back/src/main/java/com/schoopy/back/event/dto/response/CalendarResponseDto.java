package com.schoopy.back.event.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CalendarResponseDto {
    private Long eventCode;
    private String title;
    private String start;
    private String end;
}