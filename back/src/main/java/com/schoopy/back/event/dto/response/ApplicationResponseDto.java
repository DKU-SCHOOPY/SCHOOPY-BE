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

    public static ResponseEntity<ResponseDto> submitFail() {
        ResponseDto responseBody = new ResponseDto(EventResponseCode.SUBMIT_FAIL, EventResponseMessage.SUBMIT_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}