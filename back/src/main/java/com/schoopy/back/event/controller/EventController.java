package com.schoopy.back.event.controller;

import com.schoopy.back.event.dto.request.SubmitSurveyRequestDto;
import com.schoopy.back.event.dto.response.SubmitSurveyResponseDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoopy.back.event.dto.request.RegistEventRequestDto;
import com.schoopy.back.event.dto.request.RemitRequestDto;
import com.schoopy.back.event.dto.response.RegistEventResponseDto;
import com.schoopy.back.event.dto.response.RemitResponseDto;
import com.schoopy.back.event.service.EventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/schoopy/v1/event")
public class EventController {
    private final EventService eventService;

    @PostMapping("/regist-event")
    public ResponseEntity<? super RegistEventResponseDto> registEvent(
        @RequestBody @Valid RegistEventRequestDto requestBody
    ) {
        ResponseEntity<? super RegistEventResponseDto> response = eventService.registEvent(requestBody);
        return response;
    }



    @PostMapping("submit-survey")
    public ResponseEntity<? super SubmitSurveyResponseDto> submitSurvey(
            @RequestBody @Valid SubmitSurveyRequestDto requestBody
    ) {
        ResponseEntity<? super SubmitSurveyResponseDto> respose = eventService.submitSurvey(requestBody);
        return respose;
    }


    @PostMapping("/remit-event")
    public ResponseEntity<? super RemitResponseDto> remitEvent(
        @RequestBody @Valid RemitRequestDto requestBodyDto
    ) {
        ResponseEntity<? super RemitResponseDto> response = eventService.remitEvent(requestBodyDto);
        return response;
    }
}
