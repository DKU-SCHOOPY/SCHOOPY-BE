package com.schoopy.back.fcm.service;

import org.springframework.http.ResponseEntity;
import com.schoopy.back.fcm.dto.response.DepartmentFcmResponseDto;

import com.schoopy.back.fcm.dto.request.DepartmentFcmRequestDto;
import com.schoopy.back.fcm.dto.request.FcmMessageDto;

public interface FcmService {
    void sendMessageTo(FcmMessageDto dto);
    void sendMessageByTopic(String title, String body);
    ResponseEntity<? super DepartmentFcmResponseDto> sendMessageToDepartment(DepartmentFcmRequestDto dto);
}
