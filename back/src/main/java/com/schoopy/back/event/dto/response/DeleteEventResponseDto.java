package com.schoopy.back.event.dto.response;

import lombok.Getter;
import lombok.Setter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.event.common.EventResponseCode;
import com.schoopy.back.event.common.EventResponseMessage;
import com.schoopy.back.global.dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteEventResponseDto {
    private Boolean deleteStatus;

    public static ResponseEntity<DeleteEventResponseDto> success(boolean status) {
        DeleteEventResponseDto body = new DeleteEventResponseDto(status);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static ResponseEntity<ResponseDto> notFound() {
        ResponseDto body = new ResponseDto(EventResponseCode.NOT_FOUND, EventResponseMessage.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    public static ResponseEntity<ResponseDto> deleteFail() {
        ResponseDto body = new ResponseDto(EventResponseCode.DELETE_FAIL, EventResponseMessage.DELETE_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
