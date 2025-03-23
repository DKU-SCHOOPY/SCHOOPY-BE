package com.schoopy.back.qrScanning.dto.response;

import com.schoopy.back.qrScanning.common.EventResponseCode;
import com.schoopy.back.qrScanning.common.EventResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;

@Getter
public class RegistEventResponseDto extends ResponseDto{

    public static ResponseEntity<RegistEventResponseDto> success() {
        RegistEventResponseDto responseBody = new RegistEventResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    public static ResponseEntity<ResponseDto> registFail() {
        ResponseDto responseBody = new ResponseDto(EventResponseCode.REGIST_FAIL, EventResponseMessage.REGIST_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }
}
