package com.schoopy.back.event.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationRequestDto {
    @NotNull
    private String studentNum;

    @NotNull
    private Long eventCode;

    @NotNull
    private Boolean isStudent;

    @NotNull
    private Boolean councilFeePaid;

    @NotNull
    private Boolean isPaymentCompleted;
}
