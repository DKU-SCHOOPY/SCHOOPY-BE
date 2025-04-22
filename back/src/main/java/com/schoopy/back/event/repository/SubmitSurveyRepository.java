package com.schoopy.back.event.repository;

import com.schoopy.back.event.entity.EventEntity;
import com.schoopy.back.event.entity.SubmitSurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmitSurveyRepository extends JpaRepository<SubmitSurveyEntity, Long> {

    List<SubmitSurveyEntity> findByEventCode(EventEntity event);

    SubmitSurveyEntity findByApplicationId(Long applicationId);

    boolean existsByUser_StudentNum(String studentNum);
}
