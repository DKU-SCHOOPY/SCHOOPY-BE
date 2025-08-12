package com.schoopy.back.chat.dto.response;

import com.schoopy.back.chat.entity.ChatMessageEntity;
import lombok.Getter;

@Getter
public class ChatMessageResponseDto {

    private Long id;
    private String senderId;
    private String receiverId;
    private String content;
    private Long roomId;

    public ChatMessageResponseDto(ChatMessageEntity entity) {
        this.id = entity.getId();
        this.senderId = entity.getSenderId();
        this.receiverId = entity.getReceiverId();
        this.content = entity.getContent();
        this.roomId = entity.getRoom().getId();
    }
}