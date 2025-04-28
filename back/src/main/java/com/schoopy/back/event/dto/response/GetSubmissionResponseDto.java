package com.schoopy.back.event.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetSubmissionResponseDto {
    private Long applicationId;
    private String studentNum;
    private Boolean isStudent;
    private Boolean councilPaid;
    private Boolean isPaymentCompleted;
}