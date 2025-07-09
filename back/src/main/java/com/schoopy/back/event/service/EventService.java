package com.schoopy.back.event.service;

import com.schoopy.back.event.dto.request.RedirectRequestDto;
import com.schoopy.back.event.dto.request.ApplicationRequestDto;
import com.schoopy.back.event.dto.request.UpdatePaymentStatusRequestDto;
import com.schoopy.back.event.dto.response.*;
import com.schoopy.back.event.entity.EventEntity;
import com.schoopy.back.event.entity.ApplicationEntity;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.event.dto.request.RegistEventRequestDto;

import java.util.List;

public interface EventService {
    ResponseEntity<? super RegistEventResponseDto> registEvent(RegistEventRequestDto requestDto);
    ResponseEntity<? super ApplicationResponseDto> application(ApplicationRequestDto requestDto);
    ResponseEntity<?> getRemitUrl(RedirectRequestDto dto);
    List<EventEntity> getCurrentSurveyEvents();
    ResponseEntity<List<AnswerResponseDto>> getAnswersByApplicationId(Long applicationId);
    List<ApplicationEntity> getSubmissionsByEvent(Long eventCode);
    ResponseEntity<? super UpdatePaymentStatusResponseDto> updatePaymentStatus(UpdatePaymentStatusRequestDto dto);
    List<CalendarResponseDto> getCalendarEventsByYearAndMonth(int year, int month);
    ResponseEntity<FormResponseDto> getFormByEventCode(Long eventCode);
}