package com.schoopy.back.event.service.implement;

import com.schoopy.back.event.dto.request.RedirectRequestDto;
import com.schoopy.back.event.dto.request.ApplicationRequestDto;
import com.schoopy.back.event.dto.request.UpdatePaymentStatusRequestDto;
import com.schoopy.back.event.dto.response.CalendarResponseDto;
import com.schoopy.back.event.dto.response.RedirectResponseDto;
import com.schoopy.back.event.dto.response.ApplicationResponseDto;
import com.schoopy.back.event.dto.response.UpdatePaymentStatusResponseDto;
import com.schoopy.back.event.entity.ApplicationEntity;
import com.schoopy.back.event.repository.ApplicationRepository;
import com.schoopy.back.global.s3.S3Uploader;
import com.schoopy.back.notice.entity.NoticeEntity;
import com.schoopy.back.notice.repository.NoticeRepository;
import com.schoopy.back.user.entity.UserEntity;
import com.schoopy.back.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import com.schoopy.back.event.dto.request.RegistEventRequestDto;
import com.schoopy.back.event.dto.response.RegistEventResponseDto;
import com.schoopy.back.event.entity.EventEntity;
import com.schoopy.back.event.repository.EventRepository;
import com.schoopy.back.event.service.EventService;
import com.schoopy.back.fcm.dto.request.FcmMessageDto;
import com.schoopy.back.fcm.service.FcmService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class EventServiceImplement implements EventService{

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ApplicationRepository submitSurveyRepository;
    private final S3Uploader s3Uploader;
    private final FcmService fcmService;
    private final NoticeRepository noticeRepository;

    @Override
    public ResponseEntity<? super RegistEventResponseDto> registEvent(RegistEventRequestDto dto) {
        EventEntity eventEntity = new EventEntity();
        try {
            List<String> imageUrls = new ArrayList<>();
            if (dto.getEventImages() != null && !dto.getEventImages().isEmpty()) {
                for (MultipartFile file : dto.getEventImages()) {
                    String url = s3Uploader.upload(file, "event-images");
                    imageUrls.add(url);
                }
            }
            eventEntity.setEventName(dto.getEventName());
            eventEntity.setDepartment(dto.getDepartment());
            eventEntity.setSurveyStartDate(dto.getSurveyStartDate());
            eventEntity.setSurveyEndDate(dto.getSurveyEndDate());
            eventEntity.setEventStartDate(dto.getEventStartDate());
            eventEntity.setEventEndDate(dto.getEventEndDate());
            eventEntity.setMaxParticipants(dto.getMaxParticipants());
            eventEntity.setCurrentParticipants(0);
            eventEntity.setEventDescription(dto.getEventDescription());
            eventEntity.setEventImages(imageUrls);
            eventEntity.setQr_toss_o(dto.getQr_toss_o());
            eventEntity.setQr_toss_x(dto.getQr_toss_x());
            eventEntity.setQr_kakaopay_o(dto.getQr_kakaopay_o());
            eventEntity.setQr_kakaopay_x(dto.getQr_kakaopay_x());

            eventRepository.save(eventEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(RegistEventResponseDto.registFail());
        }
        return RegistEventResponseDto.success(
                eventEntity.getEventCode(),
                eventEntity.getEventName(),
                eventEntity.getDepartment(),
                eventEntity.getSurveyStartDate(),
                eventEntity.getSurveyEndDate(),
                eventEntity.getEventStartDate(),
                eventEntity.getEventEndDate(),
                eventEntity.getMaxParticipants(),
                eventEntity.getCurrentParticipants(),
                eventEntity.getEventDescription(),
                eventEntity.getEventImages(),
                eventEntity.getQr_toss_o(),
                eventEntity.getQr_toss_x(),
                eventEntity.getQr_kakaopay_o(),
                eventEntity.getQr_kakaopay_x()
        );
    }


    @Override
    public ResponseEntity<? super ApplicationResponseDto> application(ApplicationRequestDto dto) {
        try{
            UserEntity user = userRepository.findByStudentNum(dto.getStudentNum());
            EventEntity event = eventRepository.findByEventCode(dto.getEventCode());
            ApplicationEntity submit = new ApplicationEntity();

            if(user==null || event==null){
                return ResponseEntity.badRequest().body(ApplicationResponseDto.submitFail());
            }
            if(submitSurveyRepository.existsByUser_StudentNumAndEventCode_EventCode(dto.getStudentNum(), dto.getEventCode()))
            {
                return ResponseEntity.badRequest().body(ApplicationResponseDto.submitFail()+" 중복신청입니다.");
            }
            submit.setEventCode(event);
            submit.setUser(user);
            submit.setIsStudent(dto.getIsStudent());

            submitSurveyRepository.save(submit);

            return ResponseEntity.ok(ApplicationResponseDto.success(
                    submit.getApplicationId(),
                    submit.getEventCode(),
                    submit.getUser(),
                    submit.getIsStudent(),
                    false
            ));
        }catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ApplicationResponseDto.submitFail()+"예외발생");
        }
    }

    @Override
    public ResponseEntity<?> getRemitUrl(RedirectRequestDto dto) {
        try{
            UserEntity user = userRepository.findByStudentNum(dto.getStudentNum());
            EventEntity event = eventRepository.findByEventCode(dto.getEventCode());

            if(user==null || event==null){
                return ResponseEntity.badRequest().body(RedirectResponseDto.redirectFail()+"사용자 또는 행사를 찾을 수 없습니다.");
            }

            boolean councilFee = user.isCouncilPee();

            String remitType = dto.getRemitType().toLowerCase();
            if(councilFee){
                if(remitType.equals("toss")){
                    return ResponseEntity.ok(RedirectResponseDto.success(event.getQr_toss_o()));
                }else if(remitType.equals("kakaopay")){
                    return ResponseEntity.ok(RedirectResponseDto.success(event.getQr_kakaopay_o()));
                }
            }else if(!councilFee) {
                if(remitType.equals("toss")){
                    return ResponseEntity.ok(RedirectResponseDto.success(event.getQr_toss_x()));
                }else if(remitType.equals("kakaopay")){
                    return ResponseEntity.ok(RedirectResponseDto.success(event.getQr_kakaopay_x()));
                }
            }

            return ResponseEntity.badRequest().body(RedirectResponseDto.redirectFail()+"URL 반환에 실패하였습니다.");
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(RedirectResponseDto.redirectFail()+"서버내부에 오류가 발생하였습니다.");
        }
    }

    @Override
    public List<EventEntity> getCurrentSurveyEvents() {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul")); // 시간대 명시
        return eventRepository.findActiveSurveyEvents(today);
    }

    @Override
    public List<ApplicationEntity> getSubmissionsByEvent(Long eventCode){
        EventEntity event = eventRepository.findByEventCode(eventCode);
        if(event == null) {
            throw new IllegalArgumentException("행사가 존재하지 않습니다.");
        }
        return submitSurveyRepository.findByEventCode(event);
    }
    @Override
public ResponseEntity<? super UpdatePaymentStatusResponseDto> updatePaymentStatus(UpdatePaymentStatusRequestDto dto){
    try {
        ApplicationEntity submit = submitSurveyRepository.findByApplicationId(dto.getApplicationId());
        if (submit == null) {
            return ResponseEntity.badRequest().body(UpdatePaymentStatusResponseDto.updateFail());
        }

        EventEntity event = submit.getEventCode();

        if (dto.isChoice()) {
            if (submit.getIsPaymentCompleted()) {
                return ResponseEntity.badRequest().body(UpdatePaymentStatusResponseDto.updateFail() + "이미 승인 완료된 설문입니다.");
            }

            // 1. 결제 승인 처리
            submit.setIsPaymentCompleted(true);
            event.setCurrentParticipants(event.getCurrentParticipants() + 1);
            submitSurveyRepository.save(submit);
            eventRepository.save(event);

            // 2. FCM 알림 전송
            String targetToken = submit.getUser().getFcmToken();
            if (targetToken != null && !targetToken.isEmpty()) {
                FcmMessageDto fcmMessageDto = FcmMessageDto.builder()
                    .targetToken(targetToken)
                    .title("설문 승인 완료")
                    .body("이벤트 [" + event.getEventName() + "] 참가가 승인되었습니다.")
                    .receiver(submit.getUser().getStudentNum()) // NoticeEntity 저장을 위한 용도
                    .build();
                fcmService.sendMessageTo(fcmMessageDto);

                // 3. 알림 내역 저장
                NoticeEntity notice = new NoticeEntity();
                notice.setSender("event"); // 또는 로그인한 관리자 이메일 등
                notice.setReciever(submit.getUser().getStudentNum()); // 수신자 학번
                notice.setTitle("설문 승인 완료");
                notice.setMessage("이벤트 [" + event.getEventName() + "] 참가가 승인되었습니다.");
                notice.setRead(false); // 읽지 않음 상태
                noticeRepository.save(notice);
            }

        } else {
            // 거절 시 신청 삭제
            submitSurveyRepository.delete(submit);
        }

        return UpdatePaymentStatusResponseDto.success(submit.getIsPaymentCompleted());
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body(UpdatePaymentStatusResponseDto.updateFail() + "서버 내부 오류.");
    }
}


    @Override
    public List<CalendarResponseDto> getCalendarEventsByYearAndMonth(
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") int month
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return eventRepository.findAll().stream()
                .filter(event -> {
                    if (event.getEventStartDate() == null) return false;
                    LocalDate startDate = event.getEventStartDate(); // 이미 LocalDate라면 그대로
                    return startDate.getYear() == year && startDate.getMonthValue() == month;
                })
                .map(event -> new CalendarResponseDto(
                        event.getEventCode(),
                        event.getEventName(),
                        event.getEventStartDate().format(formatter),
                        event.getEventEndDate().format(formatter)
                ))
                .sorted(Comparator.comparing(CalendarResponseDto::getStart))
                .collect(Collectors.toList());
    }


    @Override
    public List<EventEntity> getAllEvents(){
        return eventRepository.findAll();
    }
}