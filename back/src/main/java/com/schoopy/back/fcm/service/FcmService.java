package com.schoopy.back.fcm.service;

import com.schoopy.back.fcm.dto.FcmMessageDto;

public interface FcmService {
    void sendMessageTo(FcmMessageDto dto);
    void sendMessageByTopic(String title, String body);
}
