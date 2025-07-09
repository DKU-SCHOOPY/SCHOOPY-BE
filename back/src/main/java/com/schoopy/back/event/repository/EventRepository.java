package com.schoopy.back.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoopy.back.event.entity.EventEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long>{
    EventEntity findByEventCode(Long eventCode);
}