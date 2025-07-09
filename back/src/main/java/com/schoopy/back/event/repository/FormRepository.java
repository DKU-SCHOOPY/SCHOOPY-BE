package com.schoopy.back.event.repository;

import com.schoopy.back.event.entity.EventEntity;
import com.schoopy.back.event.entity.FormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FormRepository extends JpaRepository<FormEntity, Long> {
    FormEntity findByEvent_EventCode(Long eventCode);

    @Query("SELECT f.event FROM FormEntity f WHERE f.surveyStartDate <= :today AND f.surveyEndDate >= :today")
    List<EventEntity> findActiveSurveyEvents(@Param("today") LocalDate today);

}
