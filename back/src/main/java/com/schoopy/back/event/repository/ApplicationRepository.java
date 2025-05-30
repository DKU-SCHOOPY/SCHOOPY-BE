package com.schoopy.back.event.repository;

import com.schoopy.back.event.entity.EventEntity;
import com.schoopy.back.event.entity.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {

    List<ApplicationEntity> findByEventCode(EventEntity event);

    ApplicationEntity findByApplicationId(Long applicationId);

    boolean existsByUser_StudentNumAndEventCode_EventCode(String studentNum, Long eventCode);
}