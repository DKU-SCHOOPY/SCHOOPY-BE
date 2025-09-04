package com.schoopy.back.notice.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;
import com.schoopy.back.notice.common.NoticeResponseCode;
import com.schoopy.back.notice.common.NoticeResponseMessage;

import lombok.Getter;

@Getter
public class ReadAllNoticeResponseDto extends ResponseDto{

    private ReadAllNoticeResponseDto() {
        super();
    }

    public static ResponseEntity<ReadAllNoticeResponseDto> success() {
        ReadAllNoticeResponseDto responseBody = new ReadAllNoticeResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> notPresident() {
        ResponseDto responseBody = new ResponseDto(NoticeResponseCode.NOT_A_PRESIDENT, NoticeResponseMessage.NOT_A_PRESIDENT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
