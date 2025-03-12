package com.schoopy.back.qrScanning.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.schoopy.back.global.dto.ResponseDto;
import com.schoopy.back.qrScanning.dto.request.RegistEventRequestDto;
import com.schoopy.back.qrScanning.dto.request.RemitRequestDto;
import com.schoopy.back.qrScanning.dto.response.RegistEventResponseDto;
import com.schoopy.back.qrScanning.dto.response.RemitResponseDto;
import com.schoopy.back.qrScanning.entity.EventEntity;
import com.schoopy.back.qrScanning.repository.EventRepository;
import com.schoopy.back.qrScanning.service.EventService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventServiceImplement implements EventService{

    private final EventRepository eventRepository;

    @Override
    public ResponseEntity<? super RegistEventResponseDto> registEvent(RegistEventRequestDto dto) {
        try {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setEventName(dto.getEventName());
            eventEntity.setQrURL(dto.getQrURL());
            eventRepository.save(eventEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        
        return RegistEventResponseDto.success();
    }

    @Override
    public ResponseEntity<? super RemitResponseDto> remitEvent(RemitRequestDto dto) {
        try {
            
            
        } catch (Exception e) {
            e.printStackTrace();
            return RemitResponseDto.remitFail();
        }

        return RemitResponseDto.success();
    }
    
}
