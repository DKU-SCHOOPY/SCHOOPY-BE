package com.schoopy.back.event.repository;

import com.schoopy.back.event.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
    List<AnswerEntity> findByApplication_ApplicationId(Long applicationId);
}
