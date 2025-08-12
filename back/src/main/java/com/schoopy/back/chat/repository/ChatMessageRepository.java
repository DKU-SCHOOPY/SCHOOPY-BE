package com.schoopy.back.chat.repository;

import com.schoopy.back.chat.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
    List<ChatMessageEntity> findByRoomIdOrderByCreatedAtAsc(Long roomId);
    ChatMessageEntity findTop1ByRoom_IdOrderByCreatedAtDesc(Long roomId);
}