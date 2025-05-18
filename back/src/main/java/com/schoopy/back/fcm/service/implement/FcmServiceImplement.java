package com.schoopy.back.fcm.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.schoopy.back.fcm.dto.request.DepartmentFcmRequestDto;
import com.schoopy.back.fcm.dto.request.FcmMessageDto;
import com.schoopy.back.fcm.dto.response.DepartmentFcmResponseDto;
import com.schoopy.back.fcm.repository.FcmTokenRepository;
import com.schoopy.back.fcm.service.FcmService;
import com.schoopy.back.user.entity.UserEntity;
import com.schoopy.back.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FcmServiceImplement implements FcmService{
    @Value("${fcm.topic-name:global}")
    private String topicName;
    private final UserRepository userRepository;
    private final FirebaseMessaging firebaseMessaging;

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

    @Override
    public ResponseEntity<? super DepartmentFcmResponseDto> sendMessageToDepartment(DepartmentFcmRequestDto dto) {
        List<UserEntity> users = userRepository.findAllByDepartment(dto.getDepartment());

        for (UserEntity user : users) {
            String token = user.getFcmToken();
            if (token != null && !token.isEmpty()) {
                Message message = Message.builder()
                        .setToken(token)
                        .setNotification(Notification.builder()
                                .setTitle(dto.getTitle())
                                .setBody(dto.getBody())
                                .build())
                        .build();

                try {
                    firebaseMessaging.send(message);
                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                    return DepartmentFcmResponseDto.sendFail();
                }
            }
        }

        return DepartmentFcmResponseDto.success();
    }
}
