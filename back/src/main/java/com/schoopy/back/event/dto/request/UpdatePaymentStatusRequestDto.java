package com.schoopy.back.event.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePaymentStatusRequestDto {
    private Long applicationId;
    private boolean choice; //신청 버튼 누르면 true, 반려 버튼 누르면 false
}
