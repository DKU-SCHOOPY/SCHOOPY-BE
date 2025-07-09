package com.schoopy.back.home.service.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.schoopy.back.event.entity.EventEntity;
import com.schoopy.back.event.repository.EventRepository;
import com.schoopy.back.home.service.HomeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeServiceImplement implements HomeService{

    private final EventRepository eventRepository;

    @Override
    public List<EventEntity> getAllEvents(){
        return eventRepository.findAll();
    }
    
}
