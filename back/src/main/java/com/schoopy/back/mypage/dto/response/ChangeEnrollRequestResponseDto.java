package com.schoopy.back.mypage.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;

import lombok.Getter;

@Getter
public class ChangeEnrollRequestResponseDto extends ResponseDto{
    private ChangeEnrollRequestResponseDto () {
        super();
    }

    public static ResponseEntity<ChangeEnrollRequestResponseDto> success() {
        ChangeEnrollRequestResponseDto responseBody = new ChangeEnrollRequestResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
