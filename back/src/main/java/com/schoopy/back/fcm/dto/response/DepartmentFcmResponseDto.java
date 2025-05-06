package com.schoopy.back.fcm.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.fcm.comomn.FcmResponseCode;
import com.schoopy.back.fcm.comomn.FcmResponseMessage;
import com.schoopy.back.global.dto.ResponseDto;

public class DepartmentFcmResponseDto extends ResponseDto{
    private DepartmentFcmResponseDto () {
        super();
    }

    public static ResponseEntity<DepartmentFcmResponseDto> success() {
        DepartmentFcmResponseDto responseBody = new DepartmentFcmResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> sendFail() {
    ResponseDto responseBody = new ResponseDto(FcmResponseCode.SEND_FAIL,FcmResponseMessage.SEND_FAIL);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
}
}
