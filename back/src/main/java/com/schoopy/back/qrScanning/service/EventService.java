package com.schoopy.back.qrScanning.service;

import org.springframework.http.ResponseEntity;

import com.schoopy.back.qrScanning.dto.request.RegistEventRequestDto;
import com.schoopy.back.qrScanning.dto.request.RemitRequestDto;
import com.schoopy.back.qrScanning.dto.response.RegistEventResponseDto;
import com.schoopy.back.qrScanning.dto.response.RemitResponseDto;
public interface EventService {
    ResponseEntity<? super RegistEventResponseDto> registEvent(RegistEventRequestDto requestDto);
    ResponseEntity<? super RemitResponseDto> remitEvent(RemitRequestDto requestDto);
}
