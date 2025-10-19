package com.schoopy.back.event.dto.request;

import com.google.auto.value.AutoValue.Builder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEventRequestDto {
    private Long eventCode;
    private String eventName;
    private String eventDescription;
}