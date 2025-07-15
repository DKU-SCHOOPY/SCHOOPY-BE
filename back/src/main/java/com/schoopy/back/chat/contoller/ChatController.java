package com.schoopy.back.chat.contoller;

import com.schoopy.back.chat.dto.request.ChatMessageRequestDto;
import com.schoopy.back.chat.dto.response.ChatMessageResponseDto;
import com.schoopy.back.chat.dto.response.ChatRoomResponseDto;
import com.schoopy.back.chat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoopy/v1/chat")
@RequiredArgsConstructor
@Tag(name="Chatting", description = "채팅 관련 API")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/message")
    @Operation(summary = "채팅 저장", description = "메시지를 보낸 후 저장합니다.")
    public ResponseEntity<? super ChatMessageResponseDto> sendMessage(@RequestBody ChatMessageRequestDto dto) {
        return chatService.saveMessage(dto);
    }

    @GetMapping("/room/{roomId}")
    @Operation(summary = "채팅 내역 불러오기", description = "해당 채팅방의 이전 채팅 내용을 불러옵니다.")
    public ResponseEntity<? super List<ChatMessageResponseDto>> getMessages(@PathVariable Long roomId) {
        return chatService.getMessagesByRoomId(roomId);
    }

    @GetMapping("/rooms/{userId}")
    @Operation(summary = "채팅방 목록 출력", description = "사용자가 속한 채팅방의 목록을 출력합니다.")
    public ResponseEntity<? super List<ChatRoomResponseDto>> getUserRooms(@PathVariable Long userId) {
        return chatService.getChatRoomsByUserId(userId);
    }
}