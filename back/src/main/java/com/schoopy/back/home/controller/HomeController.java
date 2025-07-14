package com.schoopy.back.home.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoopy.back.event.entity.EventEntity;
import com.schoopy.back.home.dto.request.GetEventInformationRequestDto;
import com.schoopy.back.home.dto.response.GetEventInformationResponseDto;
import com.schoopy.back.home.service.HomeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/schoopy/v1/home")
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/home") // 홈화면
    public ResponseEntity<List<EventEntity>> getAllEvents() {
        return ResponseEntity.ok(homeService.getAllEvents());
    }

    @PostMapping("/getEvent")
    public ResponseEntity<? super GetEventInformationResponseDto> getEventInformation(
        @RequestBody @Valid GetEventInformationRequestDto requestBody
    ) {
        ResponseEntity<? super GetEventInformationResponseDto> response = homeService.getEventInformation(requestBody);
        return response;
    }
    
}
