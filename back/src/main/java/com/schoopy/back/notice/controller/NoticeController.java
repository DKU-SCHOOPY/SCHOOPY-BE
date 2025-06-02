package com.schoopy.back.notice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoopy.back.notice.entity.NoticeEntity;
import com.schoopy.back.notice.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/schoopy/v1/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeRepository noticeRepository;

    @GetMapping("/{studentId}")
    public ResponseEntity<List<NoticeEntity>> getNotices(@PathVariable("studentId") String studentId) {
        // studentId는 학번 (reciever 필드에 저장되어 있다고 가정)
        List<NoticeEntity> notices = noticeRepository.findByReciever(studentId);

        // 읽지 않은 알림을 읽음 처리
        notices.stream()
            .filter(notice -> !notice.isReadCheck())
            .forEach(notice -> {
                notice.setReadCheck(true);
                noticeRepository.save(notice);
            });

        return ResponseEntity.ok(notices);
    }

}
