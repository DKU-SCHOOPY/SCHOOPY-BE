package com.schoopy.back.event.service.implement;

import com.schoopy.back.event.dto.request.RedirectRequestDto;
import com.schoopy.back.event.dto.request.ApplicationRequestDto;
import com.schoopy.back.event.dto.request.UpdatePaymentStatusRequestDto;
import com.schoopy.back.event.dto.response.*;
import com.schoopy.back.event.entity.*;
import com.schoopy.back.event.repository.*;
import com.schoopy.back.global.s3.S3Uploader;
import com.schoopy.back.notice.entity.NoticeEntity;
import com.schoopy.back.notice.repository.NoticeRepository;
import com.schoopy.back.user.entity.UserEntity;
import com.schoopy.back.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import com.schoopy.back.event.dto.request.RegistEventRequestDto;
import com.schoopy.back.event.service.EventService;
// import com.schoopy.back.fcm.dto.request.FcmMessageDto;
// import com.schoopy.back.fcm.service.FcmService;

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
    // private final FcmService fcmService;
    private final NoticeRepository noticeRepository;
    private final FormRepository formRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Override // 행사, 폼 내용 저장
    public ResponseEntity<? super RegistEventResponseDto> registEvent(RegistEventRequestDto dto) {
        EventEntity eventEntity = new EventEntity();

        try {
            // 1. 이미지 업로드
            List<String> imageUrls = new ArrayList<>();
            if (dto.getEventImages() != null && !dto.getEventImages().isEmpty()) {
                for (MultipartFile file : dto.getEventImages()) {
                    String url = s3Uploader.upload(file, "event-images");
                    imageUrls.add(url);
                }
            }

            // 2. 이벤트 정보 저장
            eventEntity.setEventName(dto.getEventName());
            eventEntity.setDepartment(dto.getDepartment());
            eventEntity.setEventStartDate(dto.getEventStartDate());
            eventEntity.setEventEndDate(dto.getEventEndDate());
            eventEntity.setEventDescription(dto.getEventDescription());
            eventEntity.setEventImages(imageUrls);
            eventRepository.save(eventEntity);

            // 3. 폼 내용 저장
            FormEntity form = new FormEntity();

            form.setEvent(eventEntity);
            form.setSurveyStartDate(dto.getSurveyStartDate());
            form.setSurveyEndDate(dto.getSurveyEndDate());
            form.setMaxParticipants(dto.getMaxParticipants());
            form.setCurrentParticipants(0);
            form.setQr_toss_o(dto.getQr_toss_o());
            form.setQr_toss_x(dto.getQr_toss_x());
            form.setQr_kakaopay_o(dto.getQr_kakaopay_o());
            form.setQr_kakaopay_x(dto.getQr_kakaopay_x());
            formRepository.save(form);

            // 4. 질문 리스트 저장
            if(dto.getQuestions() != null){
                for(RegistEventRequestDto.QuestionDto questionDto : dto.getQuestions()){
                    QuestionEntity question = new QuestionEntity();
                    question.setForm(form);
                    question.setQuestionText(questionDto.getQuestionText());
                    question.setQuestionType(QuestionEntity.QuestionType.valueOf(questionDto.getQuestionType()));
                    question.setChoices(questionDto.getChoices());
                    question.setRequired(questionDto.isRequired());
                    question.setMultiple(questionDto.isMultiple());
                    questionRepository.save(question);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(RegistEventResponseDto.registFail());
        }
        return RegistEventResponseDto.success();
    }

    @Override // 폼 내용 전달
    public ResponseEntity<FormResponseDto> getFormByEventCode(Long eventCode) {
        try {
            FormEntity form = formRepository.findByEvent_EventCode(eventCode);
            if(form==null) {
                return ResponseEntity.notFound().build();
            }

            List<QuestionResponseDto> questions = form.getQuestions().stream()
                    .map(q -> QuestionResponseDto.builder()
                            .questionId(q.getQuestionId())
                            .questionText(q.getQuestionText())
                            .questionType(q.getQuestionType().name())
                            .isRequired(q.isRequired())
                            .isMultiple(q.isMultiple())
                            .choices(q.getChoices())
                            .build()
                    ).toList();

            FormResponseDto responseDto = FormResponseDto.builder()
                    .formId(form.getFormId())
                    .surveyStartDate(form.getSurveyStartDate())
                    .surveyEndDate(form.getSurveyEndDate())
                    .maxParticipants(form.getMaxParticipants())
                    .currentParticipants(form.getCurrentParticipants())
                    .questions(questions)
                    .build();

            return ResponseEntity.ok(responseDto);
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override // 사용자 응답 저장
    public ResponseEntity<? super ApplicationResponseDto> application(ApplicationRequestDto dto) {
        try{
            UserEntity user = userRepository.findByStudentNum(dto.getStudentNum());
            EventEntity event = eventRepository.findByEventCode(dto.getEventCode());
            if(user==null || event==null){
                //없는 사용자 혹은 이벤트에 접근
                return ResponseEntity.badRequest().body(ApplicationResponseDto.submitFail()+"존재하지 않는 사용자 혹은 행사입니다.");
            }
            if(submitSurveyRepository.existsByUser_StudentNumAndEventCode_EventCode(dto.getStudentNum(), dto.getEventCode())) {
                //중복 신청
                return ResponseEntity.badRequest().body(ApplicationResponseDto.submitFail()+" 중복신청입니다.");
            }

            ApplicationEntity submit = new ApplicationEntity();
            submit.setUser(user);
            submit.setEventCode(event);
            submit.setIsStudent(dto.getIsStudent());
            submitSurveyRepository.save(submit);

            if(dto.getAnswer() != null) {
                for(ApplicationRequestDto.AnswerDto answerDto : dto.getAnswer()) {
                    QuestionEntity question = questionRepository.findById(answerDto.getQuestionId()).orElse(null);
                    if(question==null) continue;

                    AnswerEntity answer = new AnswerEntity();
                    answer.setApplication(submit);
                    answer.setQuestion(question);
                    answer.setAnswerText(answerDto.getAnswerText());
                    List<String> safeList = answerDto.getAnswerList()!=null ? answerDto.getAnswerList() : new ArrayList<>();
                    answer.setAnswerList(safeList);

                    answerRepository.save(answer);
                }
            }

            return ApplicationResponseDto.success();
        }catch(Exception e) {
            e.printStackTrace();
            return ApplicationResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<?> getRemitUrl(RedirectRequestDto dto) {
        try{
            UserEntity user = userRepository.findByStudentNum(dto.getStudentNum());
            EventEntity event = eventRepository.findByEventCode(dto.getEventCode());
            FormEntity form = formRepository.findByEvent_EventCode(dto.getEventCode());
            if(user==null || event==null){
                return ResponseEntity.badRequest().body(RedirectResponseDto.redirectFail()+"사용자 또는 행사를 찾을 수 없습니다.");
            }

            boolean councilFee = user.isCouncilPee();

            String remitType = dto.getRemitType().toLowerCase();
            if(councilFee){
                if(remitType.equals("toss")){
                    return ResponseEntity.ok(RedirectResponseDto.success(form.getQr_toss_o()));
                }else if(remitType.equals("kakaopay")){
                    return ResponseEntity.ok(RedirectResponseDto.success(form.getQr_kakaopay_o()));
                }
            }else if(!councilFee) {
                if(remitType.equals("toss")){
                    return ResponseEntity.ok(RedirectResponseDto.success(form.getQr_toss_x()));
                }else if(remitType.equals("kakaopay")){
                    return ResponseEntity.ok(RedirectResponseDto.success(form.getQr_kakaopay_x()));
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
        return formRepository.findActiveSurveyEvents(today);
    }

    @Override
    public ResponseEntity<List<AnswerResponseDto>> getAnswersByApplicationId(Long applicationId) {
        try {
            ApplicationEntity application = submitSurveyRepository.findById(applicationId).orElse(null);
            if (application == null) {
                return ResponseEntity.badRequest().build();
            }

            List<AnswerEntity> answers = answerRepository.findByApplication_ApplicationId(applicationId);

            List<AnswerResponseDto> response = answers.stream().map(answer -> {
                QuestionEntity question = answer.getQuestion();
                return new AnswerResponseDto(
                        question.getQuestionId(),
                        question.getQuestionText(),
                        answer.getAnswerText(),
                        answer.getAnswerList()
                );
            }).collect(Collectors.toList());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
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
            FormEntity form = formRepository.findByEvent_EventCode(event.getEventCode());
            // 1. 결제 승인 처리
            submit.setIsPaymentCompleted(true);
            form.setCurrentParticipants(form.getCurrentParticipants() + 1);
            submitSurveyRepository.save(submit);
            formRepository.save(form);

            // 3. 알림 내역 저장
                NoticeEntity notice = new NoticeEntity();
                notice.setSender("event"); // 또는 로그인한 관리자 이메일 등
                notice.setReciever(submit.getUser().getStudentNum()); // 수신자 학번
                notice.setTitle("설문 승인 완료");
                notice.setMessage("이벤트 [" + event.getEventName() + "] 참가가 승인되었습니다.");
                notice.setReadCheck(false); // 읽지 않음 상태
                noticeRepository.save(notice);
            // 2. FCM 알림 전송
            // String targetToken = submit.getUser().getFcmToken();
            // if (targetToken != null && !targetToken.isEmpty()) {
            //     FcmMessageDto fcmMessageDto = FcmMessageDto.builder()
            //         .targetToken(targetToken)
            //         .title("설문 승인 완료")
            //         .body("이벤트 [" + event.getEventName() + "] 참가가 승인되었습니다.")
            //         .receiver(submit.getUser().getStudentNum()) // NoticeEntity 저장을 위한 용도
            //         .build();
            //     fcmService.sendMessageTo(fcmMessageDto);
            // }

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