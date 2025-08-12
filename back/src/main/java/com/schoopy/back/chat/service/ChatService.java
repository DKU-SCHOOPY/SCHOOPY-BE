package com.schoopy.back.chat.service;

import com.schoopy.back.chat.dto.request.ChatMessageRequestDto;
import com.schoopy.back.chat.dto.response.ChatMessageResponseDto;
import com.schoopy.back.chat.dto.response.ChatRoomListItemResponseDto;
import com.schoopy.back.chat.entity.ChatRoomEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ChatService {
    ResponseEntity<? super ChatMessageResponseDto> saveMessage(ChatMessageRequestDto dto);
    ResponseEntity<? super List<ChatMessageResponseDto>> getMessagesByRoomId(Long roomId);
    ResponseEntity<? super List<ChatRoomListItemResponseDto>> getChatRoomsByUserId(String myId);
    ChatRoomEntity getOrCreateRoom(String userAId, String userBId);
}