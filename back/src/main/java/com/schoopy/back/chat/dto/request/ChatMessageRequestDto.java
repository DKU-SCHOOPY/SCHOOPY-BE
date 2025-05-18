package com.schoopy.back.chat.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequestDto {
    @NotNull
    private Long senderId;

    @NotNull
    private Long receiverId;

    @NotNull
    private String message;

    private Long roomId; // 선택적
}