package com.schoopy.back.global.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoopy.back.chat.dto.request.ChatMessageRequestDto;
import com.schoopy.back.chat.dto.response.ChatMessageResponseDto;
import com.schoopy.back.chat.entity.ChatRoomEntity;
import com.schoopy.back.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    // roomId → 세션 목록
    private final Map<Long, List<WebSocketSession>> roomSessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 예: ws://localhost:8080/ws/chat/32203027/32255590
        String[] uriParts = session.getUri().getPath().split("/");
        Long id1 = Long.parseLong(uriParts[3]);
        Long id2 = Long.parseLong(uriParts[4]);

        // 항상 작은 ID가 userA로 오도록 정렬
        Long userA = Math.min(id1, id2);
        Long userB = Math.max(id1, id2);

        // 채팅방 찾거나 생성
        ChatRoomEntity room = chatService.getOrCreateRoom(userA, userB);
        Long roomId = room.getId();

        // 세션에 roomId, senderId 저장
        session.getAttributes().put("roomId", roomId);
        session.getAttributes().put("senderId", id1); // 본인 ID를 sender로 저장

        // roomId 기준으로 세션 관리
        roomSessions.computeIfAbsent(roomId, k -> new ArrayList<>()).add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Long roomId = (Long) session.getAttributes().get("roomId");
        Long senderId = (Long) session.getAttributes().get("senderId");

        // 프론트에서 보낸 메시지 파싱
        ChatMessageRequestDto dto = objectMapper.readValue(message.getPayload(), ChatMessageRequestDto.class);
        dto.setRoomId(roomId);
        dto.setSenderId(senderId); // 보안상 프론트 값 무시

        // 저장
        ResponseEntity<? super ChatMessageResponseDto> responseEntity = chatService.saveMessage(dto);
        ChatMessageResponseDto saved = (ChatMessageResponseDto) responseEntity.getBody();

        // 브로드캐스트
        List<WebSocketSession> sessions = roomSessions.get(roomId);
        if (sessions != null) {
            String broadcast = objectMapper.writeValueAsString(saved);
            for (WebSocketSession s : sessions) {
                if (s.isOpen()) {
                    s.sendMessage(new TextMessage(broadcast));
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long roomId = (Long) session.getAttributes().get("roomId");
        roomSessions.getOrDefault(roomId, new ArrayList<>()).remove(session);
    }
}


