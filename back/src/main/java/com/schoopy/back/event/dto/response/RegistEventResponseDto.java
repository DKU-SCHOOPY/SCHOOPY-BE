package com.schoopy.back.event.dto.response;

import com.schoopy.back.event.common.EventResponseCode;
import com.schoopy.back.event.common.EventResponseMessage;
import com.schoopy.back.event.dto.request.RegistEventRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RegistEventResponseDto extends ResponseDto{
    public RegistEventResponseDto() {super();}

    public static ResponseEntity<RegistEventResponseDto> success() {
        RegistEventResponseDto responseBody = new RegistEventResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    public static ResponseEntity<ResponseDto> registFail() {
        ResponseDto responseBody = new ResponseDto(EventResponseCode.REGIST_FAIL, EventResponseMessage.REGIST_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}