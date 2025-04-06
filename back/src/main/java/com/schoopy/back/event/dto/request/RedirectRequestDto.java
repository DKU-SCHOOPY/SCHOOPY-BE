package com.schoopy.back.event.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedirectRequestDto {
    private String studentNum;
    private Long eventCode;
    private String remitType;
}