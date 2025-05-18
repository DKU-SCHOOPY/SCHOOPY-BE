package com.schoopy.back.chat.repository;

import com.schoopy.back.chat.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
    List<ChatRoomEntity> findByUserAOrUserB(Long userA, Long userB);
    Optional<ChatRoomEntity> findByUserAAndUserB(Long userA, Long userB);
    Optional<ChatRoomEntity> findByUserAAndUserBOrUserBAndUserA(Long userA, Long userB, Long userB2, Long userA2);
}