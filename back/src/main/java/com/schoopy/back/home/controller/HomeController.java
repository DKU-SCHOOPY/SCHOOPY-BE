package com.schoopy.back.home.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoopy.back.home.dto.request.GetEventInformationRequestDto;
import com.schoopy.back.home.dto.request.GetHomeRequestDto;
import com.schoopy.back.home.dto.response.GetEventInformationResponseDto;
import com.schoopy.back.home.dto.response.GetHomeListResponseDto;
import com.schoopy.back.home.service.HomeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;

    @PostMapping("/feedback") // 홈화면
    public ResponseEntity<? super GetHomeListResponseDto> home(
        @RequestBody @Valid GetHomeRequestDto requestBody
    ) {
        ResponseEntity<? super GetHomeListResponseDto> response = homeService.home(requestBody);
        return response;
    }

    @PostMapping("/get-event")
    public ResponseEntity<? super GetEventInformationResponseDto> getEventInformation(
        @RequestBody @Valid GetEventInformationRequestDto requestBody
    ) {
        ResponseEntity<? super GetEventInformationResponseDto> response = homeService.getEventInformation(requestBody);
        return response;
    }
    
}
