package com.schoopy.back.notice.service;

import org.springframework.http.ResponseEntity;
import com.schoopy.back.notice.dto.request.*;
import com.schoopy.back.notice.dto.response.*;

public interface NoticeService {
    ResponseEntity<? super StudentNoticeCheckResponseDto> checkStudentNotices(StudentNoticeCheckRequestDto dto);
    ResponseEntity<? super CouncilNoticeCheckResponseDto> checkCouncilNotices(CouncilNoticeCheckRequestDto dto);
    ResponseEntity<? super ReadAllNoticeResponseDto> readAllNotices(ReadAllNoticeRequestDto dto);
    ResponseEntity<? super ReadNoticeResponseDto> readNotice(ReadNoticeRequestDto dto);
    ResponseEntity<? super JustReadResponseDto> justRead(JustReadRequestDto dto);
    ResponseEntity<? super DeleteNoticeResponseDto> deleteNotice(DeleteNoticeRequestDto dto);
}
