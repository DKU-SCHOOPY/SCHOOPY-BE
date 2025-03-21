package com.schoopy.back.qrScanning.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;

public class RegistEventResponseDto extends ResponseDto{
    private RegistEventResponseDto () {
        super();
    }

    public static ResponseEntity<RegistEventResponseDto> success() {
        RegistEventResponseDto responseBody = new RegistEventResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
