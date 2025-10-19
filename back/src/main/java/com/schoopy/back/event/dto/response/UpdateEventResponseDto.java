package com.schoopy.back.event.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.event.common.EventResponseCode;
import com.schoopy.back.event.common.EventResponseMessage;
import com.schoopy.back.global.dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventResponseDto {
    private Boolean updateStatus;

    public static ResponseEntity<UpdateEventResponseDto> success(boolean status) {
        UpdateEventResponseDto responseBody = new UpdateEventResponseDto();
        responseBody.setUpdateStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> updateFail() {
        ResponseDto responseBody = new ResponseDto(EventResponseCode.UPDATE_FAIL, EventResponseMessage.UPDATE_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}