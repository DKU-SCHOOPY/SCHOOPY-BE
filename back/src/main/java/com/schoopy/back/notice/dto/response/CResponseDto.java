package com.schoopy.back.notice.dto.response;

import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;

import lombok.Getter;

@Getter
public class CResponseDto extends ResponseDto{
    private CResponseDto() {
        super();
    }

    public static ResponseEntity<? super CResponseDto> success() {
        CResponseDto responseBody = new CResponseDto();
        return ResponseEntity.ok().body(responseBody);
    }
}
