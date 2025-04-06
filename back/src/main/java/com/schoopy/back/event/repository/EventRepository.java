package com.schoopy.back.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoopy.back.event.entity.EventEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long>{
    EventEntity findByEventCode(Long eventCode);

    @Query("SELECT e FROM EventEntity e WHERE e.surveyStartDate <= CURRENT_DATE AND e.surveyEndDate >= CURRENT_DATE")
    List<EventEntity> findActiveSurveyEvents();
}

