package com.schoopy.back.notice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoopy.back.notice.entity.NoticeEntity;
import com.schoopy.back.user.entity.UserEntity;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long>{
    List<NoticeEntity> findByReceiverAndIsPresident(UserEntity receiver, boolean isPresident);
    int countByReceiverAndReadCheck(UserEntity receiver, boolean readCheck);
    NoticeEntity findByNoticeId(long noticeId);
    int countByReceiverAndReadCheckAndIsPresident(UserEntity receiver, boolean readCheck, boolean isPresident);
}