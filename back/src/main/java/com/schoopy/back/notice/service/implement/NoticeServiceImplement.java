package com.schoopy.back.notice.service.implement;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.schoopy.back.global.dto.ResponseDto;
import com.schoopy.back.notice.dto.request.*;
import com.schoopy.back.notice.dto.response.*;
import com.schoopy.back.notice.entity.NoticeEntity;
import com.schoopy.back.notice.repository.NoticeRepository;
import com.schoopy.back.notice.service.NoticeService;
import com.schoopy.back.user.entity.PresidentEntity;
import com.schoopy.back.user.entity.UserEntity;
import com.schoopy.back.user.repository.PresidentRepository;
import com.schoopy.back.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeServiceImplement implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;
    private final PresidentRepository presidentRepository;

    @Override
    public ResponseEntity<? super StudentNoticeCheckResponseDto> checkStudentNotices(StudentNoticeCheckRequestDto dto) {

        UserEntity user = userRepository.findByStudentNum(dto.getStudentNum());
        if (user == null) {
            return StudentNoticeCheckResponseDto.UserNotFound();
        }
        List<NoticeEntity> notices = null;
        try {
            notices = noticeRepository.findByReceiverAndIsPresident(user, false);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return StudentNoticeCheckResponseDto.success(notices);
    }

    @Override
    public ResponseEntity<? super CouncilNoticeCheckResponseDto> checkCouncilNotices(CouncilNoticeCheckRequestDto dto) {
        UserEntity user = userRepository.findByStudentNum(dto.getStudentNum());
        if (user == null) {
            return StudentNoticeCheckResponseDto.UserNotFound();
        }

        List<NoticeEntity> notices = null;
        try {
            notices = noticeRepository.findByReceiverAndIsPresident(user, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return CouncilNoticeCheckResponseDto.success(notices);
    }

    @Override
    public ResponseEntity<? super ReadAllNoticeResponseDto> readAllNotices(ReadAllNoticeRequestDto dto) {
        UserEntity user = userRepository.findByStudentNum(dto.getStudentNum());
        if (user == null) {
            return StudentNoticeCheckResponseDto.UserNotFound();
        }
        if(!dto.isPresident()){
            try {
                List<NoticeEntity> notices = noticeRepository.findByReceiverAndReadCheck(user, false);
                for(NoticeEntity notice : notices){
                    notice.setReadCheck(true);
                    noticeRepository.save(notice);
                }
                return ReadAllNoticeResponseDto.success();
                
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseDto.databaseError();
            }
        }else{
            String department = user.getDepartment();
            PresidentEntity president = presidentRepository.findByDepartment(department);
            if (president == null) {
                return ReadAllNoticeResponseDto.notPresident();
            }
            if(president.getStudentNum().equals(user.getStudentNum())){
                try {
                    List<NoticeEntity> notices = noticeRepository.findByReceiverAndIsPresident(user, true);
                    for(NoticeEntity notice : notices){
                        notice.setReadCheck(true);
                        noticeRepository.save(notice);
                    }
                    return ReadAllNoticeResponseDto.success();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseDto.databaseError();
                }
            }else{
                return ReadAllNoticeResponseDto.notPresident();
            }
            
        }
    }

    @Override
    public ResponseEntity<? super ReadNoticeResponseDto> readNotice(ReadNoticeRequestDto dto) {
        NoticeEntity notice = noticeRepository.findByNoticeId(dto.getNoticeId());
        if(notice == null){
            return ReadNoticeResponseDto.NoticeNotFound();
        }
        try {
            notice.setReadCheck(true);
            noticeRepository.save(notice);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ReadNoticeResponseDto.success(notice);
    }

    @Override
    public ResponseEntity<? super JustReadResponseDto> justRead(JustReadRequestDto dto) {
        NoticeEntity notice = noticeRepository.findByNoticeId(dto.getNoticeId());
        if(notice == null){
            return ReadNoticeResponseDto.NoticeNotFound();  
        }

        notice.setReadCheck(true);
        noticeRepository.save(notice);

        return JustReadResponseDto.success();
    }
    
    @Override
    public ResponseEntity<? super DeleteNoticeResponseDto> deleteNotice(DeleteNoticeRequestDto dto) {
        NoticeEntity notice = noticeRepository.findByNoticeId(dto.getNoticeId());
        if(notice == null){
            return ReadNoticeResponseDto.NoticeNotFound();
        }
        try {
            noticeRepository.delete(notice);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return DeleteNoticeResponseDto.success();
    }
}
