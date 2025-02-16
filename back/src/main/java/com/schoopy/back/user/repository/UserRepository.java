package com.schoopy.back.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoopy.back.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String>{
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    UserEntity findByEmail(String email);
}
