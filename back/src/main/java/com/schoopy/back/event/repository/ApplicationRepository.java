package com.schoopy.back.event.repository;

import com.schoopy.back.event.entity.EventEntity;
import com.schoopy.back.event.entity.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {

    List<ApplicationEntity> findByEventCode(EventEntity event);

    ApplicationEntity findByApplicationId(Long applicationId);

    boolean existsByUser_StudentNumAndEventCode_EventCode(String studentNum, Long eventCode);

    @Query("""
        SELECT DISTINCT a
        FROM ApplicationEntity a
        LEFT JOIN FETCH a.answers ans
        LEFT JOIN FETCH ans.question q
        WHERE a.eventCode.eventCode = :eventCode
        """)
    List<ApplicationEntity> findWithAnswersByEventCode(@Param("eventCode") Long eventCode);

    List<ApplicationEntity> findAllByEventCode(EventEntity event);
}