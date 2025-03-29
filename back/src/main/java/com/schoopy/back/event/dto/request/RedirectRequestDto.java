package com.schoopy.back.event.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedirectRequestDto {
    private Long studentNum;
    private String qrUrl;
    private String transactionId;
}
