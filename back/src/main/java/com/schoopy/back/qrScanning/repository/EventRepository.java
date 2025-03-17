package com.schoopy.back.qrScanning.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoopy.back.qrScanning.entity.EventEntity;

import jakarta.validation.constraints.NotBlank;

public interface EventRepository extends JpaRepository<EventEntity, Long>{

    Optional<EventEntity> findByEventCode(@NotBlank long eventCode);
}
