package com.schoopy.back.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoopy.back.user.entity.CertificationEntity;

import jakarta.transaction.Transactional;

public interface CertificationRepository extends JpaRepository<CertificationEntity, String>{
    CertificationEntity findBystudentNum(String studentNum);

    @Transactional
    void deleteByStudentNum(String studentNum);
}
