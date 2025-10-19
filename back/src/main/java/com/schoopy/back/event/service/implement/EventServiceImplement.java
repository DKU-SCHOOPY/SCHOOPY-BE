package com.schoopy.back.event.service.implement;

import com.schoopy.back.event.dto.request.RedirectRequestDto;
import com.schoopy.back.event.dto.request.ApplicationRequestDto;
import com.schoopy.back.event.dto.request.ApplicationStatusRequestDto;
import com.schoopy.back.event.dto.request.UpdatePaymentStatusRequestDto;
import com.schoopy.back.event.dto.response.*;
import com.schoopy.back.event.entity.*;
import com.schoopy.back.event.repository.*;
import com.schoopy.back.global.s3.S3Uploader;
import com.schoopy.back.notice.entity.NoticeEntity;
import com.schoopy.back.notice.repository.NoticeRepository;
import com.schoopy.back.user.entity.PresidentEntity;
import com.schoopy.back.user.entity.UserEntity;
import com.schoopy.back.user.repository.PresidentRepository;
import com.schoopy.back.user.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import com.schoopy.back.event.dto.request.RegistEventRequestDto;
import com.schoopy.back.event.dto.request.UpdateEventRequestDto;
import com.schoopy.back.event.service.EventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImplement implements EventService{

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ApplicationRepository submitSurveyRepository;
    private final S3Uploader s3Uploader;
    private final NoticeRepository noticeRepository;
    private final FormRepository formRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final PresidentRepository presidentRepository;

    @Transactional
    @Override // 행사, 폼 내용 저장(완료)
    public ResponseEntity<? super RegistEventResponseDto> registEvent(RegistEventRequestDto dto) {
        try{

            EventEntity eventEntity;
                // 1. 이미지 업로드
            if(dto.getEventImages() != null) {
                List<String> imageUrls = uploadImages(dto.getEventImages());

                eventEntity = saveEvent(dto, imageUrls);
            }else{
                // 2. 이벤트 정보 저장
                eventEntity = saveEvent(dto);
            }
            if(dto.getMaxParticipants() > 0) {
                // 3. 폼 내용 저장
                FormEntity form = saveForm(dto, eventEntity);
                // 4. 질문 리스트 저장
                saveQuestions(dto.getQuestions(), form);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return RegistEventResponseDto.registFail();
        }

        return RegistEventResponseDto.success();
    }
    //이미지 업로드 및 예외처리
    private List<String> uploadImages(List<MultipartFile> files) {
        List<String> urls = new ArrayList<>();
        if(files != null && !files.isEmpty()){
            for(MultipartFile file : files) {
                try{
                    urls.add(s3Uploader.upload(file, "event-images"));
                } catch(software.amazon.awssdk.services.s3.model.S3Exception e){
                    log.error("S3 업로드 실패 - 파일명: {}", file.getOriginalFilename(), e);
                    throw new RuntimeException("S3 업로드 실패", e);
                } catch(Exception e) {
                    log.error("이미지 업로드 실패 - 파일명: {}", file.getOriginalFilename(), e);
                    throw new RuntimeException("이미지 업로드 실패", e);
                }
            }
        }
        return urls;
    }
    //이벤트 정보 저장 및 예외처리
    private EventEntity saveEvent(RegistEventRequestDto dto, List<String> imageUrls) {
        EventEntity eventEntity = new EventEntity();
        try {
            eventEntity.setEventName(dto.getEventName());
            eventEntity.setDepartment(dto.getDepartment());
            eventEntity.setEventStartDate(dto.getEventStartDate());
            eventEntity.setEventEndDate(dto.getEventEndDate());
            eventEntity.setEventDescription(dto.getEventDescription());
            eventEntity.setEventImages(imageUrls);
        } catch (Exception e) {
            log.error("이벤트 저장 실패", e);
            throw new RuntimeException("이벤트 저장 실패", e);
        }
        
        return eventRepository.save(eventEntity);

    }

    //이벤트 정보 저장 및 예외처리
    private EventEntity saveEvent(RegistEventRequestDto dto) {
        EventEntity eventEntity = new EventEntity();
        try {
            eventEntity.setEventName(dto.getEventName());
            eventEntity.setDepartment(dto.getDepartment());
            eventEntity.setEventStartDate(dto.getEventStartDate());
            eventEntity.setEventEndDate(dto.getEventEndDate());
            eventEntity.setEventDescription(dto.getEventDescription());
        } catch (Exception e) {
            log.error("이벤트 저장 실패", e);
            throw new RuntimeException("이벤트 저장 실패", e);
        }
        
        return eventRepository.save(eventEntity);

    }
    // 폼 내용 저장 및 예외처리
    private FormEntity saveForm(RegistEventRequestDto dto, EventEntity eventEntity) {
        FormEntity form = new FormEntity();
        try {
            form.setEvent(eventEntity);
            form.setSurveyStartDate(dto.getSurveyStartDate());
            form.setSurveyEndDate(dto.getSurveyEndDate());
            form.setMaxParticipants(dto.getMaxParticipants());
            form.setCurrentParticipants(0);
            form.setQr_toss_o(dto.getQr_toss_o());
            form.setQr_toss_x(dto.getQr_toss_x());
            form.setQr_kakaopay_o(dto.getQr_kakaopay_o());
            form.setQr_kakaopay_x(dto.getQr_kakaopay_x());
        } catch (Exception e) {
            log.error("폼 저장 실패", e);
            throw new RuntimeException("폼 저장 실패", e);
        }
        return formRepository.save(form);
    }
    // 질문 리스트 저장 및 예외처리
    private void saveQuestions(List<RegistEventRequestDto.QuestionDto> questions, FormEntity form) {
        if (questions == null || questions.isEmpty()) return;

        for (RegistEventRequestDto.QuestionDto questionDto : questions) {
            QuestionEntity question = new QuestionEntity();
            try {
                question.setForm(form);
                question.setQuestionText(questionDto.getQuestionText());
                question.setQuestionType(QuestionEntity.QuestionType.valueOf(questionDto.getQuestionType()));
                question.setChoices(questionDto.getChoices());
                question.setRequired(questionDto.isRequired());
                question.setMultiple(questionDto.isMultiple());
                questionRepository.save(question);
            } catch (IllegalArgumentException e) {
                log.error("질문 타입 변환 실패: {}", questionDto.getQuestionType(), e);
                throw new RuntimeException("질문 타입이 올바르지 않습니다.", e);
            } catch (Exception e) {
                log.error("질문 저장 실패: {}", questionDto.getQuestionText(), e);
                throw new RuntimeException("질문 저장 실패", e);
            }
        }
    }


    @Override // 행사 폼 내용 전달(완료)
    public ResponseEntity<? super FormResponseDto> getFormByEventCode(Long eventCode) {
        FormEntity form = formRepository.findByEvent_EventCode(eventCode);
        try {
            if (form == null) {
                return FormResponseDto.formNotFound();
            }

            List<QuestionResponseDto> questions;
            try {
                questions = form.getQuestions().stream()
                        .map(q -> QuestionResponseDto.builder()
                                .questionId(q.getQuestionId())
                                .questionText(q.getQuestionText())
                                .questionType(q.getQuestionType().name())
                                .isRequired(q.isRequired())
                                .isMultiple(q.isMultiple())
                                .choices(q.getChoices())
                                .build()
                        ).toList();
            } catch (Exception e) {
                return FormResponseDto.getFail();
            }

            String eventName = (form.getEvent() != null && form.getEvent().getEventName() != null)
                ? form.getEvent().getEventName()
                : "";
            FormResponseDto responseDto = FormResponseDto.builder()
                    .formId(form.getFormId())
                    .surveyStartDate(form.getSurveyStartDate())
                    .surveyEndDate(form.getSurveyEndDate())
                    .maxParticipants(form.getMaxParticipants())
                    .currentParticipants(form.getCurrentParticipants())
                    .questions(questions)
                    .qr_toss_o(form.getQr_toss_o())
                    .qr_toss_x(form.getQr_toss_x())
                    .qr_kakaopay_o(form.getQr_kakaopay_o())
                    .qr_kakaopay_x(form.getQr_kakaopay_x())
                    .eventName(eventName)
                    .build();

            return ResponseEntity.ok(responseDto);

        } catch (Exception e) {
            return FormResponseDto.getFail();
        }
    }


    @Override // 사용자 응답 저장(완료)
    public ResponseEntity<? super ApplicationResponseDto> application(ApplicationRequestDto dto) {
        try{
            UserEntity user = userRepository.findByStudentNum(dto.getStudentNum());
            EventEntity event = eventRepository.findByEventCode(dto.getEventCode());
            if(user==null || event==null){
                //없는 사용자 혹은 이벤트에 접근
                return ApplicationResponseDto.notFound();
            }
            if(submitSurveyRepository.existsByUser_StudentNumAndEventCode_EventCode(dto.getStudentNum(), dto.getEventCode())) {
                //중복 신청
                return ResponseEntity.badRequest().body(ApplicationResponseDto.duplication());
            }

            ApplicationEntity submit = new ApplicationEntity();
            submit.setUser(user);
            submit.setEventCode(event);
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
            String title = "행사 신청";
            String message = user.getName() + "학생이 [" + event.getEventName() + "] 행사를 신청했습니다.";
            String department = event.getDepartment();
            PresidentEntity president = presidentRepository.findByDepartment(department);
            UserEntity presidentUser = userRepository.findByStudentNum(president.getStudentNum());
            NoticeEntity notice = new NoticeEntity(user, presidentUser, title, message, true);
            noticeRepository.save(notice);

            return ApplicationResponseDto.success();
        }catch(Exception e) {
            return ApplicationResponseDto.submitFail();
        }
    }


    @Override // 결제 url 반환(완료)
    public ResponseEntity<?> getRemitUrl(RedirectRequestDto dto) {
        try{
            UserEntity user = userRepository.findByStudentNum(dto.getStudentNum());
            EventEntity event = eventRepository.findByEventCode(dto.getEventCode());
            FormEntity form = formRepository.findByEvent_EventCode(dto.getEventCode());
            if(user==null){
                return ResponseEntity.badRequest().body(RedirectResponseDto.redirectFail()+"존재하지 않는 사용자입니다.");
            }else if(event==null){
                return ResponseEntity.badRequest().body(RedirectResponseDto.redirectFail()+"존재하지 않는 행사입니다.");
            }

            boolean councilFee = user.isCouncilPee();

            String remitType = dto.getRemitType().toLowerCase();
            if(!(remitType.equals("toss")||remitType.equals("kakaopay"))){
                return ResponseEntity.badRequest().body(RedirectResponseDto.redirectFail()+"비정상적인 결제 요청입니다.");
            }

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
            return ResponseEntity.badRequest().body(RedirectResponseDto.redirectFail()+"알 수 없는오류가 발생하였습니다.");
        }
    }


    @Override // 현재 모집 중인 행사 출력(완료)
    public List<ActiveEventResponseDto> getCurrentSurveyEvents() {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        return formRepository.findActiveSurveySummaries(today);
    }

    @Override // 설문 내용 출력(완료)
    public ResponseEntity<List<AnswerResponseDto>> getAnswersByApplicationId(Long applicationId) {
        if (applicationId == null || applicationId <= 0) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
        try {
            ApplicationEntity application = submitSurveyRepository.findById(applicationId).orElse(null);
            if (application == null) {
                return ResponseEntity.badRequest().build();
            }

            List<AnswerEntity> answers = answerRepository.findByApplication_ApplicationId(applicationId);
            if (answers.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            List<AnswerResponseDto> response = answers.stream().map(answer -> {
                QuestionEntity question = answer.getQuestion();
                if (question == null) {
                    return new AnswerResponseDto(null, "질문 정보 없음", answer.getAnswerText(), answer.getAnswerList());
                }
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
    public List<ApplicationSummaryResponseDto> getSubmissionsByEvent(Long eventCode){
        EventEntity event = eventRepository.findByEventCode(eventCode);
        if(event == null) throw new IllegalArgumentException("행사가 존재하지 않습니다.");

        return submitSurveyRepository.findByEventCode(event).stream()
            .map(a -> {
                UserEntity user = a.getUser();
                return ApplicationSummaryResponseDto.builder()
                    .applicationId(a.getApplicationId())
                    .studentNum(user != null ? user.getStudentNum() : null)
                    .name(user != null ? user.getName() : null)
                    .department(user != null ? user.getDepartment() : null)
                    .gender(user != null ? user.getGender() : null)
                    .councilPee(user != null && user.isCouncilPee()) // UserEntity에 맞춰 수정
                    .isPaymentCompleted(a.getIsPaymentCompleted() != null && a.getIsPaymentCompleted())
                    .build();
            })
            .toList();
    }


    @Override // 행사 승인/반려(완료)
    public ResponseEntity<? super UpdatePaymentStatusResponseDto> updatePaymentStatus(UpdatePaymentStatusRequestDto dto){
        if (dto == null || dto.getApplicationId() == null) {
        return ResponseEntity.badRequest().body(UpdatePaymentStatusResponseDto.updateFail() + "잘못된 요청입니다.");
        }
        
        try {
            ApplicationEntity submit = submitSurveyRepository.findByApplicationId(dto.getApplicationId());
            if (submit == null) {
                return ResponseEntity.badRequest().body(UpdatePaymentStatusResponseDto.updateFail()+"존재하지 않는 폼입니다.");
            }

            EventEntity event = submit.getEventCode();
            if (event == null) {
                return ResponseEntity.badRequest().body(UpdatePaymentStatusResponseDto.updateFail() + "존재하지 않는 행사입니다.");
            }
            if (dto.isChoice()) {
                if (submit.getIsPaymentCompleted()) {
                    return ResponseEntity.badRequest().body(UpdatePaymentStatusResponseDto.updateFail() + "이미 승인 완료된 설문입니다.");
                }
                FormEntity form = formRepository.findByEvent_EventCode(event.getEventCode());
                if (form == null) {
                    return ResponseEntity.badRequest().body(UpdatePaymentStatusResponseDto.updateFail() + "존재하지 않는 폼입니다.");
                }

                // 1. 결제 승인 처리
                submit.setIsPaymentCompleted(true);
                form.setCurrentParticipants(form.getCurrentParticipants() + 1);
                submitSurveyRepository.save(submit);
                formRepository.save(form);

                // 3. 알림 내역 저장

                UserEntity user = submit.getUser();
                PresidentEntity president = presidentRepository.findByDepartment(event.getDepartment());
                UserEntity presidentUser = userRepository.findByStudentNum(president.getStudentNum());
                String title = "행사 신청 승인 완료";
                String message = "행사 [" + event.getEventName() + "] 신청이 승인되었습니다.";
                NoticeEntity notice = new NoticeEntity(presidentUser, user, title, message, false);
                noticeRepository.save(notice);
            } else {
                // 거절 시 신청 삭제
                if (submit.getAnswers() != null) {
                    Hibernate.initialize(submit.getAnswers());
                    submitSurveyRepository.delete(submit);
                }

                UserEntity user = submit.getUser();
                PresidentEntity president = presidentRepository.findByDepartment(event.getDepartment());
                UserEntity presidentUser = userRepository.findByStudentNum(president.getStudentNum());
                String title = "행사 신청 반려";
                String message = "";
                if (dto.getReason() != null && !dto.getReason().isEmpty()) {
                    message += "행사 [" + event.getEventName() + "] 신청이 반려되었습니다.\n";
                    message += " 사유: " + dto.getReason();
                }else{
                    message += "행사 [" + event.getEventName() + "] 신청이 반려되었습니다.\n";
                    message += "자세한 내용은 학생회에 채팅으로 문의바랍니다.";

                }
                NoticeEntity notice = new NoticeEntity(presidentUser, user, title, message, false);
                noticeRepository.save(notice);
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
                        event.getEventEndDate().format(formatter),
                        event.getDepartment()
                ))
                .sorted(Comparator.comparing(CalendarResponseDto::getStart))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<EventListResponseDto> getEventList(String department) {
        if (department == null || department.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        var entities = eventRepository.findByDepartmentOrderByEventStartDateDesc(department);

        var items = entities.stream()
                .map(e -> EventListResponseDto.EventSummaryDto.builder()
                        .eventCode(e.getEventCode())
                        .eventName(e.getEventName())
                        .eventStartDate(e.getEventStartDate())
                        .eventEndDate(e.getEventEndDate())
                        .build())
                .collect(Collectors.toList());

        var body = EventListResponseDto.builder()
                .department(department)
                .events(items)
                .build();

        return ResponseEntity.ok(body);
    }
    @Override
    public ResponseEntity<ExportExcelDataResponseDto> exportApplicationsData(Long eventCode) {
        // 행사/폼 존재 확인
        EventEntity event = eventRepository.findByEventCode(eventCode);
        if (event == null) return ResponseEntity.badRequest().build();

        FormEntity form = formRepository.findByEvent_EventCode(eventCode);
        if (form == null) return ResponseEntity.badRequest().build();

        // 질문 정렬(표시 순서 고정: questionId 오름차순)
        List<QuestionEntity> questions = new ArrayList<>(form.getQuestions());
        questions.sort(Comparator.comparing(QuestionEntity::getQuestionId));

        // 신청 + 답변 fetch join
        List<ApplicationEntity> apps = submitSurveyRepository.findWithAnswersByEventCode(eventCode);

        // 질문별 빠른 검색용: idx 맵
        java.util.Map<Long, Integer> qIndex = new java.util.HashMap<>();
        for (int i = 0; i < questions.size(); i++) {
            qIndex.put(questions.get(i).getQuestionId(), i);
        }

        // 행 구성
        List<ExportExcelDataResponseDto.ExportSubmissionRowDto> rows = new ArrayList<>();
        for (ApplicationEntity app : apps) {
            com.schoopy.back.user.entity.UserEntity u = app.getUser();

            List<String> answers = new ArrayList<>(Collections.nCopies(questions.size(), ""));

            if (app.getAnswers() != null) {
                for (AnswerEntity ans : app.getAnswers()) {
                    QuestionEntity q = ans.getQuestion();
                    if (q == null || q.getQuestionId() == null) continue;
                    Integer idx = qIndex.get(q.getQuestionId());
                    if (idx == null) continue;

                    String cell = "";
                    if (ans.getAnswerList() != null && !ans.getAnswerList().isEmpty()) {
                        cell = String.join(", ", ans.getAnswerList());
                    } else if (ans.getAnswerText() != null) {
                        cell = ans.getAnswerText();
                    }
                    answers.set(idx, cell);
                }
            }

            rows.add(ExportExcelDataResponseDto.ExportSubmissionRowDto.builder()
                .studentNum(safe(u != null ? u.getStudentNum() : null))
                .name(safe(u != null ? u.getName() : null))
                .department(safe(u != null ? u.getDepartment() : null))
                .birthDay(u != null && u.getBirthDay() != null ? u.getBirthDay().toString() : "")
                .gender(safe(u != null ? u.getGender() : null))
                .phoneNum(safe(u != null ? u.getPhoneNum() : null))
                .enrolled(u != null && u.isEnrolled())
                .councilPee(u != null && u.isCouncilPee())
                .answers(answers)
                .build()
            );
        }

        // 질문 DTO
        List<ExportExcelDataResponseDto.ExportQuestionDto> questionDtos = questions.stream()
            .map(q -> ExportExcelDataResponseDto.ExportQuestionDto.builder()
                .questionId(q.getQuestionId())
                .questionText(q.getQuestionText())
                .build()
            ).collect(Collectors.toList());

        ExportExcelDataResponseDto body = ExportExcelDataResponseDto.builder()
            .eventCode(event.getEventCode())
            .eventName(event.getEventName())
            .questions(questionDtos)
            .rows(rows)
            .build();

        return ResponseEntity.ok(body);
        }

    private static String safe(String s) { 
        return s == null ? "" : s; 
    }

    @Override
    public ResponseEntity<? super ApplicationStatusResponseDto> getApplicationStatus(ApplicationStatusRequestDto requestDto){
        try{
            if(requestDto == null || requestDto.getEventCode() == null || requestDto.getStudentNum() == null || requestDto.getStudentNum().isBlank()) {
                return ResponseEntity.badRequest().build();
            }

            boolean status = submitSurveyRepository.existsByUser_StudentNumAndEventCode_EventCode(requestDto.getStudentNum(), requestDto.getEventCode());
            
            return ResponseEntity.ok(ApplicationStatusResponseDto.from(status));
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    @Transactional
    public ResponseEntity<? super UpdateEventResponseDto> updateEvent(UpdateEventRequestDto requestDto) {

        if (requestDto == null || requestDto.getEventCode() == null) {
            return ResponseEntity.badRequest().body(UpdateEventResponseDto.updateFail());
        }
        final boolean nothingToUpdate =
                requestDto.getEventName() == null && requestDto.getEventDescription() == null;
        if (nothingToUpdate) {
            return ResponseEntity.badRequest().body(UpdateEventResponseDto.updateFail());
        }

        EventEntity event = eventRepository.findById(requestDto.getEventCode()).orElse(null);
        if (event == null) {
            return ResponseEntity.badRequest().body(UpdateEventResponseDto.updateFail());
        }

        if (requestDto.getEventName() != null) {
            event.setEventName(requestDto.getEventName());
        }
        if (requestDto.getEventDescription() != null) {
            event.setEventDescription(requestDto.getEventDescription());
        }

        return UpdateEventResponseDto.success(true);
    }

    @Override
    @Transactional
    public ResponseEntity<? super DeleteEventResponseDto> deleteEvent(Long eventCode) {
        if (eventCode == null) 
            return ResponseEntity.badRequest().body(DeleteEventResponseDto.notFound());

        EventEntity event = eventRepository.findById(eventCode).orElse(null);
        
        if (event == null) 
            return ResponseEntity.badRequest().body(DeleteEventResponseDto.notFound());

        List<ApplicationEntity> apps = submitSurveyRepository.findAllByEventCode(event);
        if (!apps.isEmpty()) {
            submitSurveyRepository.deleteAll(apps);
        }

        formRepository.findByEvent(event).ifPresent(formRepository::delete);

        eventRepository.delete(event);

        return DeleteEventResponseDto.success(true);
    }
}