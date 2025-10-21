package com.schoopy.back.home.service.implement;

import java.util.Comparator;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.schoopy.back.event.entity.EventEntity;
import com.schoopy.back.event.entity.FormEntity;
import com.schoopy.back.event.repository.EventRepository;
import com.schoopy.back.event.repository.FormRepository;
import com.schoopy.back.global.dto.ResponseDto;
import com.schoopy.back.home.dto.request.GetEventInformationRequestDto;
import com.schoopy.back.home.dto.request.GetHomeRequestDto;
import com.schoopy.back.home.dto.response.GetEventInformationResponseDto;
import com.schoopy.back.home.dto.response.GetHomeListResponseDto;
import com.schoopy.back.home.dto.response.GetHomeResponseDto;
import com.schoopy.back.home.service.HomeService;
import com.schoopy.back.notice.repository.NoticeRepository;
import com.schoopy.back.user.repository.PresidentRepository;
import com.schoopy.back.user.repository.UserRepository;
import com.schoopy.back.user.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeServiceImplement implements HomeService{

    private final EventRepository eventRepository;
    private final FormRepository formRepository;
    private final UserRepository userRepository;
    private final PresidentRepository presidentRepository;
    private final NoticeRepository noticeRepository;

    @Override
    public ResponseEntity<? super GetHomeListResponseDto> home(GetHomeRequestDto dto) {

        UserEntity userEntity = userRepository.findByStudentNum(dto.getStudentNum());

        int noticeCount = 0;
        int pnoticeCount = 0;

        //안읽은 알림 개수 설정
        if(presidentRepository.existsByStudentNum(dto.getStudentNum())) {
            pnoticeCount = noticeRepository.countByReceiverAndReadCheckAndIsPresident(userEntity, false, true);
            noticeCount = noticeRepository.countByReceiverAndReadCheckAndIsPresident(userEntity, false, false);
            userEntity.setPNoticeCount(pnoticeCount);
            userEntity.setNoticeCount(noticeCount);
        }else {
            noticeCount = noticeRepository.countByReceiverAndReadCheck(userEntity, false);
            userEntity.setNoticeCount(noticeCount);
        }

        int nc;

        if(dto.isPresident()){
            nc = userEntity.getPNoticeCount();
        } else {
            nc = userEntity.getNoticeCount();
        }

        List<GetHomeResponseDto> events = eventRepository.findAll().stream()
        .sorted(Comparator.comparing(EventEntity::getEventCode).reversed())
        .map(event -> {
            FormEntity form = formRepository.findByEvent_EventCode(event.getEventCode());
            if (form == null) return GetHomeResponseDto.from(event);
            return GetHomeResponseDto.from(event, form);
        })
        .toList();

        return GetHomeListResponseDto.success(nc, events); 
    }



    @Override
    public ResponseEntity<? super GetEventInformationResponseDto> getEventInformation(
            GetEventInformationRequestDto dto) {
        EventEntity event;
        FormEntity form;

        try {
            event = eventRepository.findByEventCode(dto.getEventCode());
            form = formRepository.findByEvent_EventCode(dto.getEventCode());

            if(event == null) {
                return ResponseDto.badRequest();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }    

        if(form == null) {
            return GetEventInformationResponseDto.success(event);
        }else{
            return GetEventInformationResponseDto.success(event, form);
        }
    }
    
    
}
