package com.schoopy.back.event.controller;

import com.schoopy.back.event.dto.request.RedirectRequestDto;
import com.schoopy.back.event.dto.request.SubmitSurveyRequestDto;
import com.schoopy.back.event.dto.request.UpdatePaymentStatusRequestDto;
import com.schoopy.back.event.dto.response.SubmitSurveyResponseDto;
import com.schoopy.back.event.dto.response.UpdatePaymentStatusResponseDto;
import com.schoopy.back.event.entity.EventEntity;
import com.schoopy.back.event.entity.SubmitSurveyEntity;
import org.springframework.web.bind.annotation.*;

import com.schoopy.back.event.dto.request.RegistEventRequestDto;
import com.schoopy.back.event.dto.response.CalendarResponseDto;
import com.schoopy.back.event.dto.response.RegistEventResponseDto;
import com.schoopy.back.event.service.EventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequiredArgsConstructor
@RequestMapping("/schoopy/v1/event")
public class EventController {
    private final EventService eventService;

    @PostMapping("/regist-event") // 행사 게시글 등록
    public ResponseEntity<? super RegistEventResponseDto> registEvent(
        @RequestBody @Valid RegistEventRequestDto requestBody
    ) {
        ResponseEntity<? super RegistEventResponseDto> response = eventService.registEvent(requestBody);
        return response;
    }

    @PostMapping("submit-survey") // 행사 신청
    public ResponseEntity<? super SubmitSurveyResponseDto> submitSurvey(
            @RequestBody @Valid SubmitSurveyRequestDto requestBody
    ) {
        ResponseEntity<? super SubmitSurveyResponseDto> response = eventService.submitSurvey(requestBody);
        return response;
    }

    @PostMapping("/remit-event") // 사용자에 따른 토스 or 카카오페이 송금 URL 리디렉션
    public ResponseEntity<?> remitRedirect(@RequestBody @Valid RedirectRequestDto requestBody) {
        ResponseEntity<?> response = eventService.getRemitUrl(requestBody);
        return response;
    }

    @GetMapping("/events/active") // 조사 중인 행사 목록 출력
    public ResponseEntity<List<EventEntity>> getActiveEvents() {
        return ResponseEntity.ok(eventService.getCurrentSurveyEvents());
    }

    @GetMapping("/submissions/{eventCode}") // 제출된 행사 신청 설문 목록 출력
    public ResponseEntity<List<SubmitSurveyEntity>> getSubmissionsByEvent(@PathVariable Long eventCode) {
        return ResponseEntity.ok(eventService.getSubmissionsByEvent(eventCode));
    }

    @PostMapping("/approve") // 신청 설문에 대한 승인/반려 적용
    public ResponseEntity<? super UpdatePaymentStatusResponseDto> approveSubmission(@RequestBody @Valid UpdatePaymentStatusRequestDto requestBody) {
        return eventService.updatePaymentStatus(requestBody);
    }

    @GetMapping("/calendar")
    public ResponseEntity<List<CalendarResponseDto>> getCalendarEventsByYearAndMonth(
        @RequestParam(name = "year") int year,
        @RequestParam(name = "month") int month
    ) {
        List<CalendarResponseDto> events = eventService.getCalendarEventsByYearAndMonth(year, month);
        return ResponseEntity.ok(events);
}
    
}