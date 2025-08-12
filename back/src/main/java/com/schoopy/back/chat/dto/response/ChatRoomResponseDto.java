package com.schoopy.back.chat.dto.response;

import com.schoopy.back.chat.entity.ChatRoomEntity;
import lombok.Getter;

@Getter
public class ChatRoomResponseDto {
    private Long id;
    private String userA;
    private String userB;

    public ChatRoomResponseDto(ChatRoomEntity entity) {
        this.id = entity.getId();
        this.userA = entity.getUserA();
        this.userB = entity.getUserB();
    }
}