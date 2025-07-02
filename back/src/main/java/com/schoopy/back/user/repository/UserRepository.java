package com.schoopy.back.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoopy.back.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
    boolean existsByStudentNum(String studentNum);
    UserEntity findByStudentNum(String studentNum);
    List<UserEntity> findAllByDepartment(String department);
}