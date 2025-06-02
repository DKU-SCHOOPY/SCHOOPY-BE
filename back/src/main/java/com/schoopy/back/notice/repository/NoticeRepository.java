package com.schoopy.back.notice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoopy.back.notice.entity.NoticeEntity;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long>{
    List<NoticeEntity> findByReciever(String reciever);
    int countByRecieverAndReadCheckFalse(String reciever);
}
