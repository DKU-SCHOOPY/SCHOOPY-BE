package com.schoopy.back.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatRoomListItemResponseDto {
    private Long roomId;
    private String counterpartId;
    private String counterpartName;
    private String lastMessage;
    private LocalDateTime lastMessageAt;
}