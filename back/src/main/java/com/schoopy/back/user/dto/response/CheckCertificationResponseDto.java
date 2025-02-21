package com.schoopy.back.user.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;
import com.schoopy.back.user.common.UserResponseCode;
import com.schoopy.back.user.common.UserResponseMessage;

public class CheckCertificationResponseDto extends ResponseDto{
    private CheckCertificationResponseDto () {
        super();
    }

    public static ResponseEntity<CheckCertificationResponseDto> success() {
        CheckCertificationResponseDto responseBody = new CheckCertificationResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> certificationFail () {
        ResponseDto responseBody = new ResponseDto(UserResponseCode.CERTIFICATION_FAIL, UserResponseMessage.CERTIFICATION_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }
}
