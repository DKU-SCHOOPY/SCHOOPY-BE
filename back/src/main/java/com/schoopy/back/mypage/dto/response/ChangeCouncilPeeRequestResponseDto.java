package com.schoopy.back.mypage.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;

import lombok.Getter;

@Getter
public class ChangeCouncilPeeRequestResponseDto extends ResponseDto{
    private ChangeCouncilPeeRequestResponseDto () {
        super();
    }

    public static ResponseEntity<ChangeCouncilPeeRequestResponseDto> success() {
        ChangeCouncilPeeRequestResponseDto responseBody = new ChangeCouncilPeeRequestResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
