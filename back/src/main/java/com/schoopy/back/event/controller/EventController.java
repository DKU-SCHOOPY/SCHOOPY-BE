package com.schoopy.back.event.controller;

import com.schoopy.back.event.dto.request.RedirectRequestDto;
import com.schoopy.back.event.dto.request.ApplicationRequestDto;
import com.schoopy.back.event.dto.request.UpdatePaymentStatusRequestDto;
import com.schoopy.back.event.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import com.schoopy.back.event.dto.request.RegistEventRequestDto;
import com.schoopy.back.event.service.EventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import java.util.List;



@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
@Tag(name="Event", description = "행사 관련 API")
public class EventController {
    private final EventService eventService;

    @PostMapping("/council/regist-event")
    @Operation(summary = "행사 등록", description = "새로운 행사 정보를 등록합니다.")
    public ResponseEntity<? super RegistEventResponseDto> registEvent(
            @ModelAttribute RegistEventRequestDto requestBody
    ) {
        return ResponseEntity.ok(eventService.registEvent(requestBody));
    }

    @GetMapping("/student/get-form/{eventCode}")
    @Operation(summary = "폼 내용 조회", description = "폼을 구성하기 위한 내용을 불러옵니다.")
    public ResponseEntity<? super FormResponseDto> getForm(@PathVariable Long eventCode){
        return eventService.getFormByEventCode(eventCode);
    }

    @PostMapping("/student/application") // 행사 신청
    @Operation(summary = "행사 신청", description = "행사에 신청한 내용을 저장합니다.")
    public ResponseEntity<? super ApplicationResponseDto> application(
            @RequestBody @Valid ApplicationRequestDto requestBody
    ) {
        ResponseEntity<? super ApplicationResponseDto> response = eventService.application(requestBody);
        return response;
    }

    @PostMapping("/student/remit-redirect") // 사용자에 따른 토스 or 카카오페이 송금 URL 리디렉션
    @Operation(summary = "결제 URL 반환", description = "사용자가 결제할 URL을 반환합니다.")
    public ResponseEntity<?> remitRedirect(@RequestBody @Valid RedirectRequestDto requestBody) {
        ResponseEntity<?> response = eventService.getRemitUrl(requestBody);
        return response;
    }

    @GetMapping("/student/get-active")
    @Operation(summary = "신청 가능 행사 확인", description = "현재 신청 받고 있는 행사 요약 정보를 출력합니다.")
    public ResponseEntity<List<ActiveEventResponseDto>> getActiveEvents() {
        return ResponseEntity.ok(eventService.getCurrentSurveyEvents());
    }


    @GetMapping("/council/application/{applicationId}/answers")
    @Operation(summary = "폼 응답 내용 조회", description = "사용자가 폼 질문에 응답한 내용을 출력합니다.")
    public ResponseEntity<List<AnswerResponseDto>> getAnswers(
            @PathVariable Long applicationId
    ) {
        return eventService.getAnswersByApplicationId(applicationId);
    }


    @GetMapping("/council/submissions/{eventCode}")
    @Operation(summary = "신청 폼 출력", description = "행사에 제출된 폼 목록을 반환합니다.")
    public ResponseEntity<List<ApplicationSummaryResponseDto>> getSubmissionsByEvent(@PathVariable Long eventCode) {
        return ResponseEntity.ok(eventService.getSubmissionsByEvent(eventCode));
    }

    @PostMapping("/council/approve") // 신청 설문에 대한 승인/반려 적용
    @Operation(summary = "신청 승인/반려", description = "제출 폼에 대한 승인/반려가 가능합니다.")
    public ResponseEntity<? super UpdatePaymentStatusResponseDto> approveSubmission(@RequestBody @Valid UpdatePaymentStatusRequestDto requestBody) {
        return eventService.updatePaymentStatus(requestBody);
    }

    @GetMapping("all/calendar")
    @Operation(summary = "캘린더", description = "캘린더에 등록된 행사 일정을 출력합니다.")
    public ResponseEntity<List<CalendarResponseDto>> getCalendarEventsByYearAndMonth(
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") int month
    ) {
        List<CalendarResponseDto> events = eventService.getCalendarEventsByYearAndMonth(year, month);
        return ResponseEntity.ok(events);
    }

    @GetMapping("council/{department}/get-event")
    @Operation(summary = "행사 목록", description="학생회 주관 행사 목록을 출력")
    public ResponseEntity<EventListResponseDto> getEventList(@PathVariable String department){
        return eventService.getEventList(department);
    }

    @GetMapping("/council/{eventCode}/export-data")
    @Operation(summary = "행사 신청내역(엑셀용 데이터)", description = "엑셀 생성을 위한 JSON 스키마와 데이터 반환")
    public ResponseEntity<ExportExcelDataResponseDto> exportData(@PathVariable Long eventCode) {
        return eventService.exportApplicationsData(eventCode);
    }
}