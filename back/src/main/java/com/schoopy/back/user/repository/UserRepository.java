package com.schoopy.back.user.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoopy.back.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String>{
    boolean existsByStudentNum(String studentNum);
    UserEntity findByStudentNum(String studentNum);
    List<UserEntity> findAllByDepartment(String department);
    UserEntity findByKakaoId(String kakaoId);
    UserEntity findByNaverId(String naverId);
    List<UserEntity> findByStudentNumIn(Collection<String> studentNums);
}