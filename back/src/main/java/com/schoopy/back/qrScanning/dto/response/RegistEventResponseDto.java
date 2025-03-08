package com.schoopy.back.qrScanning.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RegistEventResponseDto {
    private RegistEventResponseDto () {
        super();
    }

    public static ResponseEntity<RegistEventResponseDto> success() {
        RegistEventResponseDto responseBody = new RegistEventResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
