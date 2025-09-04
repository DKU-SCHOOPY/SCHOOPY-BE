package com.schoopy.back.notice.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;

import lombok.Getter;

@Getter
public class DeleteNoticeResponseDto extends ResponseDto{
    private DeleteNoticeResponseDto() {
        super();
    }

    public static ResponseEntity<? super DeleteNoticeResponseDto> success() {
        DeleteNoticeResponseDto responseBody = new DeleteNoticeResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
