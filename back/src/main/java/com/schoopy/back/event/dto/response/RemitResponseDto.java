package com.schoopy.back.event.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;
import com.schoopy.back.event.common.EventResponseCode;
import com.schoopy.back.event.common.EventResponseMessage;

public class RemitResponseDto extends ResponseDto{
    private RemitResponseDto () {
        super();
    }

    public static ResponseEntity<RemitResponseDto> success() {
        RemitResponseDto responseBody = new RemitResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> remitFail() {
        ResponseDto responseBody = new ResponseDto(EventResponseCode.REMIT_FAIL, EventResponseMessage.REMIT_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }
}
