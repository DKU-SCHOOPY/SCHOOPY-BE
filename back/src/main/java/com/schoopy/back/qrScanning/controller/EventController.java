package com.schoopy.back.qrScanning.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoopy.back.qrScanning.dto.request.RegistEventRequestDto;
import com.schoopy.back.qrScanning.dto.response.RegistEventResponseDto;
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
    
    private final EventService EventService;
    @PostMapping("/regist-event")
    public ResponseEntity<? super RegistEventResponseDto> registEvent(
        @RequestBody @Valid RegistEventRequestDto requestBody
    ) {
        ResponseEntity<? super RegistEventResponseDto> response = EventService.registEvent(requestBody);
        return response;
    }
    

}
