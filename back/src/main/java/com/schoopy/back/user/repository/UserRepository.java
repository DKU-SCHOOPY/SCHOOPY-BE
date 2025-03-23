package com.schoopy.back.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoopy.back.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String>{
    boolean existsByStudentNum(String studentNum);

    UserEntity findByStudentNum(String studentNum);
}
