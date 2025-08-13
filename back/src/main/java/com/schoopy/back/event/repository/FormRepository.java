package com.schoopy.back.event.repository;

import com.schoopy.back.event.dto.response.ActiveEventResponseDto;
import com.schoopy.back.event.entity.FormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FormRepository extends JpaRepository<FormEntity, Long> {
    FormEntity findByEvent_EventCode(Long eventCode);

    @Query("""
    SELECT new com.schoopy.back.event.dto.response.ActiveEventResponseDto(
        e.eventCode,
        e.eventName,
        e.department,
        f.formId,
        f.surveyStartDate,
        f.surveyEndDate,
        f.maxParticipants,
        f.currentParticipants
    )
    FROM FormEntity f
    JOIN f.event e
    WHERE f.surveyStartDate <= :today AND f.surveyEndDate >= :today
""")
    List<ActiveEventResponseDto> findActiveSurveySummaries(@Param("today") LocalDate today);


}
