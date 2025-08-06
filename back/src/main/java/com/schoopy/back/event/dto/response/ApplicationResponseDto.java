package com.schoopy.back.event.dto.response;

import com.schoopy.back.global.dto.ResponseDto;
import com.schoopy.back.event.common.EventResponseCode;
import com.schoopy.back.event.common.EventResponseMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class ApplicationResponseDto extends ResponseDto {
    public ApplicationResponseDto() {
        super();
    }

    public static ResponseEntity<ApplicationResponseDto> success() {
        ApplicationResponseDto responseBody = new ApplicationResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<? super ApplicationResponseDto> submitFail() {
        ResponseDto responseBody = new ResponseDto(EventResponseCode.SUBMIT_FAIL, EventResponseMessage.SUBMIT_FAIL);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }

    public static ResponseEntity<? super ApplicationResponseDto> notFound() {
        ResponseDto responseBody = new ResponseDto(EventResponseCode.NOT_FOUND, EventResponseMessage.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }

    public static ResponseEntity<? super ApplicationResponseDto> duplication() {
        ResponseDto responseBody = new ResponseDto(EventResponseCode.DUPLICATION, EventResponseMessage.DUPLICATION);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}