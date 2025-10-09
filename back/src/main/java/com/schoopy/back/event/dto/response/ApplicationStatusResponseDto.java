package com.schoopy.back.event.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationStatusResponseDto {
    private boolean applicationStatus;

    public static ApplicationStatusResponseDto from(boolean status) {
        return ApplicationStatusResponseDto.builder()
            .applicationStatus(status)
            .build();
    }
}
