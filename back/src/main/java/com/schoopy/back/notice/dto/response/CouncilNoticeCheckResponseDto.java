package com.schoopy.back.notice.dto.response;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;
import com.schoopy.back.notice.common.NoticeResponseCode;
import com.schoopy.back.notice.common.NoticeResponseMessage;
import com.schoopy.back.notice.entity.NoticeEntity;

import lombok.Getter;

@Getter
public class CouncilNoticeCheckResponseDto extends ResponseDto{
    private List<NoticeEntity> notices;

    private CouncilNoticeCheckResponseDto (List<NoticeEntity> notices) {
        super();
        this.notices = notices;
    }

    private CouncilNoticeCheckResponseDto () {
        super();
    }

    public static ResponseEntity<CouncilNoticeCheckResponseDto> success(List<NoticeEntity> notices) {
        CouncilNoticeCheckResponseDto responseBody = new CouncilNoticeCheckResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> UserNotFound() {
        ResponseDto responseBody = new ResponseDto(NoticeResponseCode.USER_NOT_FOUND, NoticeResponseMessage.USER_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
