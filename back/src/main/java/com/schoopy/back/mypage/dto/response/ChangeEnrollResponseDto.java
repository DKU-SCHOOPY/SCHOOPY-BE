package com.schoopy.back.mypage.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;

import lombok.Getter;

@Getter
public class ChangeEnrollResponseDto extends ResponseDto{
    private ChangeEnrollResponseDto () {
        super();
    }

    public static ResponseEntity<ChangeEnrollResponseDto> success() {
        ChangeEnrollResponseDto responseBody = new ChangeEnrollResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
