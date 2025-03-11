package com.schoopy.back.qrScanning.service;

import org.springframework.http.ResponseEntity;

import com.schoopy.back.qrScanning.dto.request.RegistEventRequestDto;
import com.schoopy.back.qrScanning.dto.response.RegistEventResponseDto;
public interface EventService {
    ResponseEntity<? super RegistEventResponseDto> registEvent(RegistEventRequestDto dto);
}
