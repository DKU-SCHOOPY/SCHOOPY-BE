package com.schoopy.back.chat.service.implement;

import com.schoopy.back.chat.dto.request.ChatMessageRequestDto;
import com.schoopy.back.chat.dto.response.ChatMessageResponseDto;
import com.schoopy.back.chat.dto.response.ChatRoomResponseDto;
import com.schoopy.back.chat.entity.ChatMessageEntity;
import com.schoopy.back.chat.entity.ChatRoomEntity;
import com.schoopy.back.chat.repository.ChatMessageRepository;
import com.schoopy.back.chat.repository.ChatRoomRepository;
import com.schoopy.back.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImplement implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public ResponseEntity<? super ChatMessageResponseDto> saveMessage(ChatMessageRequestDto dto) {
        Long senderId = dto.getSenderId();
        Long receiverId = dto.getReceiverId();

        if (senderId.equals(receiverId)) {
            throw new IllegalArgumentException("자기 자신에게 메시지를 보낼 수 없습니다.");
        }

        Long userA = Math.min(senderId, receiverId);
        Long userB = Math.max(senderId, receiverId);

        ChatRoomEntity room = chatRoomRepository.findByUserAAndUserB(userA, userB)
                .orElseGet(() -> {
                    ChatRoomEntity newRoom = ChatRoomEntity.builder()
                            .userA(userA)
                            .userB(userB)
                            .build();
                    return chatRoomRepository.save(newRoom);
                });

        ChatMessageEntity message = ChatMessageEntity.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .content(dto.getMessage())
                .room(room)
                .build();

        chatMessageRepository.save(message);
        return ResponseEntity.ok(new ChatMessageResponseDto(message));
    }

    @Override
    public ChatRoomEntity getOrCreateRoom(Long senderId, Long receiverId) {
        Long userA = Math.min(senderId, receiverId);
        Long userB = Math.max(senderId, receiverId);

        return chatRoomRepository.findByUserAAndUserB(userA, userB)
                .orElseGet(() -> {
                    ChatRoomEntity room = ChatRoomEntity.builder()
                            .userA(userA)
                            .userB(userB)
                            .build();
                    return chatRoomRepository.save(room);
                });
    }

    @Override
    public ResponseEntity<? super List<ChatMessageResponseDto>> getMessagesByRoomId(Long roomId) {
        List<ChatMessageEntity> messages = chatMessageRepository.findByRoomIdOrderByCreatedAtAsc(roomId);
        List<ChatMessageResponseDto> response = messages.stream()
                .map(ChatMessageResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<? super List<ChatRoomResponseDto>> getChatRoomsByUserId(Long userId) {
        List<ChatRoomEntity> rooms = chatRoomRepository.findByUserAOrUserB(userId, userId);
        List<ChatRoomResponseDto> response = rooms.stream()
                .map(ChatRoomResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}