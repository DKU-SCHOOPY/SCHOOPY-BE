package com.schoopy.back.fcm.service.implement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.schoopy.back.fcm.dto.FcmMessageDto;
import com.schoopy.back.fcm.service.FcmService;

@Service
public class FcmServiceImplement implements FcmService{
    @Value("${fcm.topic-name:global}")
    private String topicName;

    @Override
    public void sendMessageTo(FcmMessageDto dto) {
        try {
            Message message = Message.builder()
                    .setToken(dto.getTargetToken())
                    .setNotification(Notification.builder()
                            .setTitle(dto.getTitle())
                            .setBody(dto.getBody())
                            .build())
                    .build();
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message to token: " + response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessageByTopic(String title, String body) {
        try {
            Message message = Message.builder()
                    .setTopic(topicName)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .build();
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message to topic: " + response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }
}
