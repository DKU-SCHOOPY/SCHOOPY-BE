package com.schoopy.back.notice.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;
import com.schoopy.back.notice.common.NoticeResponseCode;
import com.schoopy.back.notice.common.NoticeResponseMessage;
import com.schoopy.back.notice.entity.NoticeEntity;

import lombok.Getter;

@Getter
public class ReadNoticeResponseDto extends ResponseDto{

    private NoticeEntity notice;

    private ReadNoticeResponseDto(NoticeEntity notice) {
        super();
        this.notice = notice;
    }

    public static ResponseEntity<? super ReadNoticeResponseDto> success(NoticeEntity notice) {
        ReadNoticeResponseDto responseBody = new ReadNoticeResponseDto(notice);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> NoticeNotFound() {
        ResponseDto responseBody = new ResponseDto(NoticeResponseCode.NOTICE_NOT_FOUND, NoticeResponseMessage.NOTICE_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }
    
}
