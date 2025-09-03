package com.schoopy.back.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoopy.back.user.dto.request.ElectRequestDto;
import com.schoopy.back.user.dto.response.ElectResponseDto;
import com.schoopy.back.user.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/president")
@Tag(name="President", description = "학회장 관련 API")
public class PresidentController {
    
    private final UserService userService;

    @PostMapping("/elect")
    public ResponseEntity<? super ElectResponseDto> elect(
        @RequestBody @Valid ElectRequestDto requestBody
    ) {
        ResponseEntity<? super ElectResponseDto> response = userService.elect(requestBody);
        return response;
    }
}
