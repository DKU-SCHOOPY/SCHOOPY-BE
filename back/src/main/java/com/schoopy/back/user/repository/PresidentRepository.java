package com.schoopy.back.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoopy.back.user.entity.PresidentEntity;

public interface PresidentRepository extends JpaRepository<PresidentEntity, String>{
    PresidentEntity findByDepartment(String department);
    PresidentEntity findByStudentNum(String studentNum);
}
