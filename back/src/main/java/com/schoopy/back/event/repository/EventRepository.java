package com.schoopy.back.event.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoopy.back.event.entity.EventEntity;

public interface EventRepository extends JpaRepository<EventEntity, Long>{
    EventEntity findByEventCode(Long eventCode);
    List<EventEntity> findByDepartmentOrderByEventStartDateDesc(String department);
}