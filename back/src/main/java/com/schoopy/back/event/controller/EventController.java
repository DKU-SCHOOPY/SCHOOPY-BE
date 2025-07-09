package com.schoopy.back.event.controller;

import com.schoopy.back.event.dto.request.RedirectRequestDto;
import com.schoopy.back.event.dto.request.ApplicationRequestDto;
import com.schoopy.back.event.dto.request.UpdatePaymentStatusRequestDto;
import com.schoopy.back.event.dto.response.*;
import com.schoopy.back.event.entity.EventEntity;
import com.schoopy.back.event.entity.ApplicationEntity;
import org.springframework.web.bind.annotation.*;

import com.schoopy.back.event.dto.request.RegistEventRequestDto;
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

    @PostMapping("/regist-event")
    public ResponseEntity<? super RegistEventResponseDto> registEvent(
            @ModelAttribute RegistEventRequestDto requestBody
    ) {
        return ResponseEntity.ok(eventService.registEvent(requestBody));
    }

    @GetMapping("/get-form/{eventCode}")
    public ResponseEntity<FormResponseDto> getForm(@PathVariable Long eventCode){
        return eventService.getFormByEventCode(eventCode);
    }

    @PostMapping("application") // 행사 신청
    public ResponseEntity<? super ApplicationResponseDto> application(
            @RequestBody @Valid ApplicationRequestDto requestBody
    ) {
        ResponseEntity<? super ApplicationResponseDto> response = eventService.application(requestBody);
        return response;
    }

    @PostMapping("/remit-redirect") // 사용자에 따른 토스 or 카카오페이 송금 URL 리디렉션
    public ResponseEntity<?> remitRedirect(@RequestBody @Valid RedirectRequestDto requestBody) {
        ResponseEntity<?> response = eventService.getRemitUrl(requestBody);
        return response;
    }

    // 학생회 구분도 해야함!
    @GetMapping("/get-active") // 조사 중인 행사 목록 출력
    public ResponseEntity<List<EventEntity>> getActiveEvents() {
        return ResponseEntity.ok(eventService.getCurrentSurveyEvents());
    }

    @GetMapping("/application/{applicationId}/answers")
    public ResponseEntity<List<AnswerResponseDto>> getAnswers(
            @PathVariable Long applicationId
    ) {
        return eventService.getAnswersByApplicationId(applicationId);
    }


    @GetMapping("/submissions/{eventCode}") // 제출된 행사 신청 설문 목록 출력
    public ResponseEntity<List<ApplicationEntity>> getSubmissionsByEvent(@PathVariable Long eventCode) {
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