package com.schoopy.back.event.service.implement;

import com.schoopy.back.event.dto.request.SubmitSurveyRequestDto;
import com.schoopy.back.event.dto.response.SubmitSurveyResponseDto;
import com.schoopy.back.event.entity.SubmitSurveyEntity;
import com.schoopy.back.event.repository.SubmitSurveyRepository;
import com.schoopy.back.user.entity.UserEntity;
import com.schoopy.back.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.schoopy.back.global.dto.ResponseDto;
import com.schoopy.back.event.dto.request.RegistEventRequestDto;
import com.schoopy.back.event.dto.request.RemitRequestDto;
import com.schoopy.back.event.dto.response.RegistEventResponseDto;
import com.schoopy.back.event.dto.response.RemitResponseDto;
import com.schoopy.back.event.entity.EventEntity;
import com.schoopy.back.event.repository.EventRepository;
import com.schoopy.back.event.service.EventService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventServiceImplement implements EventService{

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final SubmitSurveyRepository submitSurveyRepository;

    @Override
    public ResponseEntity<? super RegistEventResponseDto> registEvent(RegistEventRequestDto dto) {
        EventEntity eventEntity = new EventEntity();
        try {
            eventEntity.setEventName(dto.getEventName());
            eventEntity.setSurveyStartDate(dto.getSurveyStartDate());
            eventEntity.setSurveyEndDate(dto.getSurveyEndDate());
            eventEntity.setEventStartDate(dto.getEventStartDate());
            eventEntity.setEventEndDate(dto.getEventEndDate());
            eventEntity.setMaxParticipants(dto.getMaxParticipants());
            eventEntity.setCurrentParticipants(0);
            eventEntity.setEventDescription(dto.getEventDescription());
            eventEntity.setEventImages(dto.getEventImages());
            eventEntity.setQrCodeImages(dto.getQrCodeImages());
            eventRepository.save(eventEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return RegistEventResponseDto.success(
            eventEntity.getEventCode(),
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
    public ResponseEntity<? super SubmitSurveyResponseDto> submitSurvey(SubmitSurveyRequestDto dto) {
        try{
            UserEntity user = userRepository.findByStudentNum(dto.getStudentNum());
            EventEntity event = eventRepository.findByEventCode(dto.getEventCode());
            SubmitSurveyEntity submit = new SubmitSurveyEntity();

            if(user==null || event==null){
                return ResponseEntity.badRequest().body(SubmitSurveyResponseDto.submitFail());
            }
            if(submit.getIsPaymentCompleted()==false){
                return ResponseEntity.badRequest().body(SubmitSurveyResponseDto.submitFail());
            }

            submit.setEventCode(event);
            submit.setStudentNum(user);
            submit.setIsStudent(dto.getIsStudent());
            submit.setCouncilFeePaid(dto.getCouncilFeePaid());
            submit.setIsPaymentCompleted(dto.getIsPaymentCompleted());

            submitSurveyRepository.save(submit);

            return ResponseEntity.ok(SubmitSurveyResponseDto.success(
                    submit.getApplicationId(),
                    submit.getEventCode(),
                    submit.getStudentNum(),
                    submit.getIsStudent(),
                    submit.getCouncilFeePaid(),
                    submit.getIsPaymentCompleted()
            ));
        }catch(Exception e) {
            return ResponseEntity.badRequest().body(SubmitSurveyResponseDto.submitFail());
        }
    }

    @Override
    public ResponseEntity<? super RemitResponseDto> remitEvent(RemitRequestDto dto) {
        try {
            
            
        } catch (Exception e) {
            e.printStackTrace();
            return RemitResponseDto.remitFail();
        }

        return RemitResponseDto.success();
    }
    
}
