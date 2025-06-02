package com.schoopy.back.event.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationRequestDto {
    @NotNull
    private String studentNum; // 학번
    @NotNull
    private Long eventCode; // 행사 코드
    @NotNull
    private Boolean isStudent; // 재학생 여부

    private Boolean isPaymentCompleted; // 행사비용 납부여부
}