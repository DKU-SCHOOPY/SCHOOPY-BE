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
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    // roomId → 세션 목록
    private final Map<Long, List<WebSocketSession>> roomSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 예: ws://host/ws/chat/32203027/32255590
        String[] parts = session.getUri().getPath().split("/");
        String pathA = parts[3]; // 접속자(이 소켓의 사용자) 학번
        String pathB = parts[4]; // 상대 학번

        // 정렬해서 방 키 결정
        String userA = pathA.compareTo(pathB) < 0 ? pathA : pathB;
        String userB = pathA.compareTo(pathB) < 0 ? pathB : pathA;

        // 방 조회/생성 (String 버전)
        ChatRoomEntity room = chatService.getOrCreateRoom(userA, userB);
        Long roomId = room.getId();

        // 세션 속성 저장
        session.getAttributes().put("roomId", roomId);
        session.getAttributes().put("senderId", pathA);      // 이 소켓 소유자
        session.getAttributes().put("counterpartId", pathB); // 상대

        // roomId 기준 세션 등록
        roomSessions.computeIfAbsent(roomId, k -> Collections.synchronizedList(new ArrayList<>()))
                    .add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Long roomId = (Long) session.getAttributes().get("roomId");
        String senderId = (String) session.getAttributes().get("senderId");
        String receiverId = (String) session.getAttributes().get("counterpartId");

        // 프론트 payload 파싱 (message 필드만 써도 됨)
        ChatMessageRequestDto dto = objectMapper.readValue(message.getPayload(), ChatMessageRequestDto.class);
        dto.setRoomId(roomId);
        dto.setSenderId(senderId);    // 보안상 프론트 값 무시
        dto.setReceiverId(receiverId);// 보안상 프론트 값 무시

        // 저장
        ResponseEntity<? super ChatMessageResponseDto> savedResp = chatService.saveMessage(dto);
        ChatMessageResponseDto saved = (ChatMessageResponseDto) savedResp.getBody();

        // 브로드캐스트
        List<WebSocketSession> sessions = roomSessions.get(roomId);
        if (sessions != null) {
            String payload = objectMapper.writeValueAsString(saved);
            synchronized (sessions) {
                for (WebSocketSession s : sessions) {
                    if (s.isOpen()) s.sendMessage(new TextMessage(payload));
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long roomId = (Long) session.getAttributes().get("roomId");
        List<WebSocketSession> sessions = roomSessions.get(roomId);
        if (sessions != null) {
            synchronized (sessions) {
                sessions.remove(session);
                if (sessions.isEmpty()) {
                    roomSessions.remove(roomId);
                }
            }
        }
    }
}
