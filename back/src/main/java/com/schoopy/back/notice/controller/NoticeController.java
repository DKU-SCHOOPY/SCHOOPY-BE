package com.schoopy.back.notice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoopy.back.notice.dto.request.*;
import com.schoopy.back.notice.dto.response.*;
import com.schoopy.back.notice.service.NoticeService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
@Tag(name="Notice", description = "알림 관련 API")
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping("/student/check")
    @Operation(summary = "알림 조회", description = "저장된 알림을 불러옵니다.")
    public ResponseEntity<? super StudentNoticeCheckResponseDto> studentNoticeCheck(
        @RequestBody @Valid StudentNoticeCheckRequestDto requestBody
    ) {
        ResponseEntity<? super StudentNoticeCheckResponseDto> response = noticeService.checkStudentNotices(requestBody);
        return response;
    }

    @PostMapping("/council/check")
    public ResponseEntity<? super CouncilNoticeCheckResponseDto> councilNoticeCheck(
        @RequestBody @Valid CouncilNoticeCheckRequestDto requestBody
    ) {
        ResponseEntity<? super CouncilNoticeCheckResponseDto> response = noticeService.checkCouncilNotices(requestBody);
        return response;
    }

    @PostMapping("all/readAll")
    public ResponseEntity<? super ReadAllNoticeResponseDto> readAllNotices(
        @RequestBody @Valid ReadAllNoticeRequestDto requestBody
    ) {
        ResponseEntity<? super ReadAllNoticeResponseDto> response = noticeService.readAllNotices(requestBody);
        return response;
    }

    @PostMapping("all/read")
    public ResponseEntity<? super ReadNoticeResponseDto> readNotice(
        @RequestBody @Valid ReadNoticeRequestDto requestBody
    ) {
        ResponseEntity<? super ReadNoticeResponseDto> response = noticeService.readNotice(requestBody);
        return response;    
    }

    @PostMapping("all/justRead")
    public ResponseEntity<? super JustReadResponseDto> justRead(
        @RequestBody @Valid JustReadRequestDto requestBody
    ) {
        ResponseEntity<? super JustReadResponseDto> response = noticeService.justRead(requestBody);
        return response;
    }

    @PostMapping("all/delete")
    public ResponseEntity<? super DeleteNoticeResponseDto> deleteNotice(
        @RequestBody @Valid DeleteNoticeRequestDto requestBody
    ) {
        ResponseEntity<? super DeleteNoticeResponseDto> response = noticeService.deleteNotice(requestBody);
        return response;
    }

    @PostMapping("council/Erequest")
    public ResponseEntity<? super EResponseDto> eRequest(
        @RequestBody @Valid ERequestDto requestBody
    ) {
        ResponseEntity<? super EResponseDto> response = noticeService.eRequest(requestBody);
        return response;
    }

    @PostMapping("council/Crequest")
    public ResponseEntity<? super CResponseDto> cRequest(
        @RequestBody @Valid CRequestDto requestBody
    ) {
        ResponseEntity<? super CResponseDto> response = noticeService.cRequest(requestBody);
        return response;
    }
    
}
