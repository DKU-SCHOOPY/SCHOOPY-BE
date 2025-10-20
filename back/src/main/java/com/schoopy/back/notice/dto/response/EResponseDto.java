package com.schoopy.back.notice.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;

import lombok.Getter;

@Getter
public class EResponseDto extends ResponseDto{
    private EResponseDto() {
        super();
    }

    public static ResponseEntity<? super EResponseDto> success() {
        EResponseDto responseBody = new EResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
