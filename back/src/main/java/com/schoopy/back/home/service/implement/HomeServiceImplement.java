package com.schoopy.back.home.service.implement;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.schoopy.back.event.entity.EventEntity;
import com.schoopy.back.event.repository.EventRepository;
import com.schoopy.back.global.dto.ResponseDto;
import com.schoopy.back.home.dto.request.GetEventInformationRequestDto;
import com.schoopy.back.home.dto.response.GetEventInformationResponseDto;
import com.schoopy.back.home.dto.response.GetHomeResponseDto;
import com.schoopy.back.home.service.HomeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeServiceImplement implements HomeService{

    private final EventRepository eventRepository;

    @Override
    public List<GetHomeResponseDto> home(){

        
        return eventRepository.findAll().stream()
                .map(event -> new GetHomeResponseDto(
                        event.getEventCode()))
                .toList();
    }

    @Override
    public ResponseEntity<? super GetEventInformationResponseDto> getEventInformation(
            GetEventInformationRequestDto dto) {
        EventEntity event;
        
        try {
            event = eventRepository.findByEventCode(dto.getEventCode());

            if(event == null) {
                return ResponseDto.badRequest();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }    
        return GetEventInformationResponseDto.success(event);
    }
    
    
}
