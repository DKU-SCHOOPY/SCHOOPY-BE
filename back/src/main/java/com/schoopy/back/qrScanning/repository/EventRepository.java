package com.schoopy.back.qrScanning.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.schoopy.back.qrScanning.entity.EventEntity;

public interface EventRepository extends JpaRepository<EventEntity, Long>{

    Optional<EventEntity> findByEventCode(@NonNull String eventCode);
}
