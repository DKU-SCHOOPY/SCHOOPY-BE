package com.schoopy.back.global.dto;

import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.common.ResponseCode;
import com.schoopy.back.global.common.ResponseMessage;

import org.springframework.http.HttpStatus;

public class OAuthHelplerResponseDto extends ResponseDto {
    private OAuthHelplerResponseDto() {
        super();
    }

    public static ResponseEntity<OAuthHelplerResponseDto> success() {
        OAuthHelplerResponseDto responseBody = new OAuthHelplerResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> oAuthError() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.OAUTH_ERROR, ResponseMessage.OAUTH_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
}
