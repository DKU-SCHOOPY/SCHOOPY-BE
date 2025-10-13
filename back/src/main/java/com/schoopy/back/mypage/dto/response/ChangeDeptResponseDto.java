package com.schoopy.back.mypage.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;

import lombok.Getter;

@Getter
public class ChangeDeptResponseDto extends ResponseDto{
    private ChangeDeptResponseDto () {
        super();
    }

    public static ResponseEntity<ChangeDeptResponseDto> success() {
        ChangeDeptResponseDto responseBody = new ChangeDeptResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
