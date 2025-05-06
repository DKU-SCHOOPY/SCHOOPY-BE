package com.schoopy.back.fcm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoopy.back.fcm.dto.request.DepartmentFcmRequestDto;
import com.schoopy.back.fcm.service.FcmService;
import com.schoopy.back.fcm.dto.response.DepartmentFcmResponseDto;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/schoopy/v1/fcm")
public class FcmContoller {
    private final FcmService fcmService;

    @PostMapping("path")
    public ResponseEntity<? super DepartmentFcmResponseDto> departmentNotificationSend(
        @RequestBody @Valid DepartmentFcmRequestDto requestBody
    ){
        ResponseEntity<? super DepartmentFcmResponseDto> response = fcmService.sendMessageToDepartment(requestBody);
        return response;
    }
}
