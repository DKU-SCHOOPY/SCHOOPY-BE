package com.schoopy.back.qrScanning.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.schoopy.back.qrScanning.dto.request.RegistEventRequestDto;
import com.schoopy.back.qrScanning.dto.response.RegistEventResponseDto;
import com.schoopy.back.qrScanning.entity.EventEntity;
import com.schoopy.back.qrScanning.repository.EventRepository;
import com.schoopy.back.qrScanning.service.EventService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventServiceImplement implements EventService{

    private final EventRepository eventRepository;

    @Override
    public ResponseEntity<? super RegistEventResponseDto> saveEvent(RegistEventRequestDto dto) {
        EventEntity event = EventEntity.builder()
            .eventCode(dto.getEventCode())
            .qrURL(dto.getQrURL())                
            .build();
        eventRepository.save(event);
        
        return RegistEventResponseDto.success();
    }
    
}
