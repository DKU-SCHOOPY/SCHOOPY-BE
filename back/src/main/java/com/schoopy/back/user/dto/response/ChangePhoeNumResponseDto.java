package com.schoopy.back.user.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;

public class ChangePhoeNumResponseDto extends ResponseDto{
    private ChangePhoeNumResponseDto () {
        super();
    }

    public static ResponseEntity<ChangePhoeNumResponseDto> success() {
        ChangePhoeNumResponseDto responseBody = new ChangePhoeNumResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
