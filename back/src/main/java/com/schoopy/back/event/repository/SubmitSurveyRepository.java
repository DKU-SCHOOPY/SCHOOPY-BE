package com.schoopy.back.event.repository;

import com.schoopy.back.event.entity.SubmitSurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmitSurveyRepository extends JpaRepository<SubmitSurveyEntity, Long> {
}
