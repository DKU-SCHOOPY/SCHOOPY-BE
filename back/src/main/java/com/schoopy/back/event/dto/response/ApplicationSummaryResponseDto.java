package com.schoopy.back.event.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApplicationSummaryResponseDto {
    private Long applicationId;
    private String studentNum;
    private String name;
    private String department;
    private String gender;
    private boolean councilPee;     // 회비 납부 여부 (UserEntity에 있다면 매핑)
    private boolean isPaymentCompleted; // 신청 승인 여부
}
 