package com.schoopy.back.notice.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;

import lombok.Getter;

@Getter
public class JustReadResponseDto extends ResponseDto{
    
    private JustReadResponseDto() {
        super();
    }

    public static ResponseEntity<? super JustReadResponseDto> success() {
        JustReadResponseDto responseBody = new JustReadResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
