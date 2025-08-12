package com.schoopy.back.chat.repository;

import com.schoopy.back.chat.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
    List<ChatRoomEntity> findByUserAOrUserB(String userA, String userB);
    Optional<ChatRoomEntity> findByUserAAndUserB(String userA, String userB);
    Optional<ChatRoomEntity> findByUserAAndUserBOrUserBAndUserA(String userA, String userB, String userB2, String userA2);
}