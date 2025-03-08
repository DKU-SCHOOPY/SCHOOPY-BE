package com.schoopy.back.qrScanning.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistEventRequestDto {
    private String eventCode;
    private String qrURL;
}
