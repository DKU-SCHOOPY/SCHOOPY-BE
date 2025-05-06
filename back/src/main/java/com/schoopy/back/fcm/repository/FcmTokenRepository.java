package com.schoopy.back.fcm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoopy.back.fcm.entity.FcmTokenEntity;

public interface FcmTokenRepository extends JpaRepository<FcmTokenEntity, Long>{
    Optional<FcmTokenEntity> findByStudentNum(String studentNum);
}
