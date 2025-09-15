package com.schoopy.back.event.dto.response;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class EventListResponseDto {
    private String department;
    private List<EventSummaryDto> events;

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor @Builder
    public static class EventSummaryDto {
        private Long eventCode;
        private String eventName;
        private LocalDate eventStartDate;
        private LocalDate eventEndDate;
    }
}