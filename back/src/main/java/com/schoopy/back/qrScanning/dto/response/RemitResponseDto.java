package com.schoopy.back.qrScanning.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RemitResponseDto {
    private RemitResponseDto () {
        super();
    }

    public static ResponseEntity<RemitResponseDto> success() {
        RemitResponseDto responseBody = new RemitResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
