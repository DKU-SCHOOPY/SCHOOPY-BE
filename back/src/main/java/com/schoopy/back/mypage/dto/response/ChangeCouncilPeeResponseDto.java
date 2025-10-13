package com.schoopy.back.mypage.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;

public class ChangeCouncilPeeResponseDto extends ResponseDto{
    private ChangeCouncilPeeResponseDto () {
        super();
    }

    public static ResponseEntity<ChangeCouncilPeeResponseDto> success() {
        ChangeCouncilPeeResponseDto responseBody = new ChangeCouncilPeeResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
