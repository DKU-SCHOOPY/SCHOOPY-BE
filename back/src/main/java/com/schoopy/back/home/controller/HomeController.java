package com.schoopy.back.home.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoopy.back.home.dto.request.GetEventInformationRequestDto;
import com.schoopy.back.home.dto.response.GetEventInformationResponseDto;
import com.schoopy.back.home.dto.response.GetHomeResponseDto;
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
    public ResponseEntity<List<GetHomeResponseDto>> home() {
        return ResponseEntity.ok(homeService.home());
    }

    @PostMapping("/get-event")
    public ResponseEntity<? super GetEventInformationResponseDto> getEventInformation(
        @RequestBody @Valid GetEventInformationRequestDto requestBody
    ) {
        ResponseEntity<? super GetEventInformationResponseDto> response = homeService.getEventInformation(requestBody);
        return response;
    }
    
}
