package com.schoopy.back.event.service;

import com.schoopy.back.event.dto.request.SubmitSurveyRequestDto;
import com.schoopy.back.event.dto.response.SubmitSurveyResponseDto;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.event.dto.request.RegistEventRequestDto;
import com.schoopy.back.event.dto.request.RemitRequestDto;
import com.schoopy.back.event.dto.response.RegistEventResponseDto;
import com.schoopy.back.event.dto.response.RemitResponseDto;
public interface EventService {
    ResponseEntity<? super RegistEventResponseDto> registEvent(RegistEventRequestDto requestDto);
    ResponseEntity<? super SubmitSurveyResponseDto> submitSurvey(SubmitSurveyRequestDto requestDto);
    ResponseEntity<? super RemitResponseDto> remitEvent(RemitRequestDto requestDto);
}
