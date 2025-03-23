package com.schoopy.back.qrScanning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoopy.back.qrScanning.dto.request.RegistEventRequestDto;
import com.schoopy.back.qrScanning.dto.request.RemitRequestDto;
import com.schoopy.back.qrScanning.dto.response.RegistEventResponseDto;
import com.schoopy.back.qrScanning.dto.response.RemitResponseDto;
import com.schoopy.back.qrScanning.service.EventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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

    @PostMapping("/remit-event")
    public ResponseEntity<? super RemitResponseDto> remitEvent(
        @RequestBody @Valid RemitRequestDto requestBodyDto
    ) {
        ResponseEntity<? super RemitResponseDto> response = eventService.remitEvent(requestBodyDto);
        return response;
    }
}
