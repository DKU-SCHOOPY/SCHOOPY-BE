package com.schoopy.back.qrScanning.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;

public class RemitResponseDto extends ResponseDto{
    private RemitResponseDto () {
        super();
    }

    public static ResponseEntity<RemitResponseDto> success() {
        RemitResponseDto responseBody = new RemitResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
