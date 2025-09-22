package com.schoopy.back.home.dto.response;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.event.entity.EventEntity;
import com.schoopy.back.event.entity.FormEntity;
import com.schoopy.back.global.dto.ResponseDto;

import lombok.Getter;

@Getter
public class GetEventInformationResponseDto extends ResponseDto {

    private Long eventCode;
    private String eventName;
    private String department;
    private String eventDescription;
    private List<String> eventImages;
    private LocalDate surveyStartDate;
    private LocalDate surveyEndDate;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
    private int maxParticipant;

    
    private GetEventInformationResponseDto(EventEntity event, FormEntity form) {
        super();
        this.eventCode = event.getEventCode();
        this.eventName = event.getEventName();
        this.department = event.getDepartment();
        this.eventDescription = event.getEventDescription();
        this.eventImages = event.getEventImages();
        this.surveyStartDate = form.getSurveyStartDate();
        this.surveyEndDate = form.getSurveyEndDate();
        this.eventStartDate = event.getEventStartDate();
        this.eventEndDate = event.getEventEndDate();
        this.maxParticipant = form.getMaxParticipants();
    }

    private GetEventInformationResponseDto(EventEntity event) {
        super();
        this.eventCode = event.getEventCode();
        this.eventName = event.getEventName();
        this.department = event.getDepartment();
        this.eventDescription = event.getEventDescription();
        this.eventImages = event.getEventImages();
        this.surveyStartDate = null;
        this.surveyEndDate = null;
        this.eventStartDate = event.getEventStartDate();
        this.eventEndDate = event.getEventEndDate();
        this.maxParticipant = 0;
    }

    public static ResponseEntity<GetEventInformationResponseDto> success(EventEntity event, FormEntity form) {
        GetEventInformationResponseDto responseBody = new GetEventInformationResponseDto(event, form);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    } 

    public static ResponseEntity<GetEventInformationResponseDto> success(EventEntity event) {
        GetEventInformationResponseDto responseBody = new GetEventInformationResponseDto(event);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

}
