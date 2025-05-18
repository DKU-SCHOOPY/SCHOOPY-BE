package com.schoopy.back.chat.contoller;

import com.schoopy.back.chat.dto.request.ChatMessageRequestDto;
import com.schoopy.back.chat.dto.response.ChatMessageResponseDto;
import com.schoopy.back.chat.dto.response.ChatRoomResponseDto;
import com.schoopy.back.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoopy/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/message")
    public ResponseEntity<? super ChatMessageResponseDto> sendMessage(@RequestBody ChatMessageRequestDto dto) {
        return chatService.saveMessage(dto);
    }

    @GetMapping("/room/{roomId}/messages")
    public ResponseEntity<? super List<ChatMessageResponseDto>> getMessages(@PathVariable Long roomId) {
        return chatService.getMessagesByRoomId(roomId);
    }

    @GetMapping("/rooms/{userId}")
    public ResponseEntity<? super List<ChatRoomResponseDto>> getUserRooms(@PathVariable Long userId) {
        return chatService.getChatRoomsByUserId(userId);
    }
}