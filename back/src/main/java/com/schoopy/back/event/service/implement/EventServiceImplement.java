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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImplement implements EventService{

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ApplicationRepository submitSurveyRepository;
    private final S3Uploader s3Uploader;
    private final FcmService fcmService;

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
            eventEntity.setQrCodeImages(dto.getQrCodeImages());

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
                eventEntity.getQrCodeImages()
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
            if(submitSurveyRepository.existsByUser_StudentNum(dto.getStudentNum())){
                return ResponseEntity.badRequest().body(ApplicationResponseDto.submitFail());
            }
            submit.setEventCode(event);
            submit.setUser(user);
            submit.setIsStudent(dto.getIsStudent());
            submit.setCouncilFeePaid(dto.getCouncilFeePaid());

            submitSurveyRepository.save(submit);

            return ResponseEntity.ok(ApplicationResponseDto.success(
                    submit.getApplicationId(),
                    submit.getEventCode(),
                    submit.getUser(),
                    submit.getIsStudent(),
                    submit.getCouncilFeePaid(),
                    false
            ));
        }catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ApplicationResponseDto.submitFail());
        }
    }

    @Override
    public ResponseEntity<?> getRemitUrl(RedirectRequestDto dto) {
        try{
            UserEntity user = userRepository.findByStudentNum(dto.getStudentNum());
            EventEntity event = eventRepository.findByEventCode(dto.getEventCode());

            if(user==null || event==null){
                return ResponseEntity.badRequest().body(RedirectResponseDto.redirectFail()+"찾을 수 없는 행사입니다.");
            }

            boolean councilFee = user.isCouncilPee();
            List<String> qrList = event.getQrCodeImages();
            if(qrList == null || qrList.size() < 4){
                return ResponseEntity.badRequest().body(RedirectResponseDto.redirectFail()+"QR코드 이미지가 부족합니다.");
            }

            String remitType = dto.getRemitType().toLowerCase();
            int index;

            if(remitType.equals("toss")) {
                index = councilFee ? 0 : 1;
            }else if(remitType.equals("kakaopay")) {
                index = councilFee ? 2 : 3;
            }else{
                return ResponseEntity.badRequest().body(RedirectResponseDto.redirectFail()+"결제수단이 잘못되었습니다.");
            }

            String redirectUrl = qrList.get(index);
            return ResponseEntity.ok(RedirectResponseDto.success(redirectUrl));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(RedirectResponseDto.redirectFail()+"서버내부에 오류가 발생하였습니다.");
        }
    }

    @Override
    public List<EventEntity> getCurrentSurveyEvents(){
        return eventRepository.findActiveSurveyEvents();
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
            String token = submit.getUser().getFcmToken();
            String title, body;

            if (dto.isChoice()) {
                if (submit.getIsPaymentCompleted()) {
                    return ResponseEntity.badRequest().body(UpdatePaymentStatusResponseDto.updateFail()+"이미 승인 완료된 설문입니다.");
                }
                submit.setIsPaymentCompleted(true);
                event.setCurrentParticipants(event.getCurrentParticipants() + 1);

                title = event.getEventName() + "신청 승인 완료";
                body = event.getEventName() + "신청이 승인되었습니다.";
            } else {
                submitSurveyRepository.delete(submit);

                title = event.getEventName() + "신청 반려";
                body = event.getEventName() + "행사의 신청이 학생회의 요청으로 인해 거절되었습니다. 자세한 문의사항은 메시지 기능을 이용하여 학생회에 문의하시기 바랍니다.";
            }
            eventRepository.save(event);
            submitSurveyRepository.save(submit);

            if(token != null && !token.isEmpty()){
                FcmMessageDto fcmMessage = new FcmMessageDto(token, title, body);
                fcmService.sendMessageTo(fcmMessage);
            }

            return UpdatePaymentStatusResponseDto.success(submit.getIsPaymentCompleted());
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(UpdatePaymentStatusResponseDto.updateFail()+"서버 내부 오류.");
        }
    }

    @Override
    public List<CalendarResponseDto> getCalendarEventsByYearAndMonth(
        @RequestParam(name = "year") int year,
        @RequestParam(name = "month") int month
    ) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return eventRepository.findAll().stream()
            .filter(event -> {
                if (event.getEventStartDate() == null) return false;
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                calendar.setTime(event.getEventStartDate());
                int eventYear = calendar.get(java.util.Calendar.YEAR);
                int eventMonth = calendar.get(java.util.Calendar.MONTH) + 1; // 0-based
                return eventYear == year && eventMonth == month;
            })
            .map(event -> new CalendarResponseDto(
                event.getEventCode(),
                event.getEventName(),
                formatter.format(event.getEventStartDate()),
                formatter.format(event.getEventEndDate())
            ))
            .sorted((e1, e2) -> e1.getStart().compareTo(e2.getStart()))
            .collect(Collectors.toList());
    }
}