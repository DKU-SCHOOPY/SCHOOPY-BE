package com.schoopy.back.user.dto.response;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;

import lombok.Getter;

@Getter
public class ElectResponseDto extends ResponseDto{
    private ElectResponseDto() {
        super();
    }

    public static ResponseEntity<ElectResponseDto> success() {
        ElectResponseDto responseBody = new ElectResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
