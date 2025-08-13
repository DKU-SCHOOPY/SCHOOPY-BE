package com.schoopy.back.home.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import com.schoopy.back.event.entity.EventEntity;
import com.schoopy.back.event.entity.FormEntity;
import com.schoopy.back.event.repository.EventRepository;
import com.schoopy.back.event.repository.FormRepository;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetHomeResponseDto {

    private EventRepository eventRespository;
    private FormRepository formRepository;

    private Long eventCode;
    private String eventName;
    private String department;
    private LocalDate serveyStartDate;
    private LocalDate serveyEndDate;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
    private int maxParticipant;
    private String eventDescription;
    private List<String> eventImages;

    public GetHomeResponseDto(Long eventCode) {
        EventEntity event = eventRespository.findByEventCode(eventCode);
        FormEntity form = formRepository.findByEvent_EventCode(eventCode);
        this.eventCode = event.getEventCode();
        this.eventName = event.getEventName();
        this.department = event.getDepartment();
        this.serveyStartDate = form.getSurveyStartDate();
        this.serveyEndDate = form.getSurveyEndDate();
        this.eventStartDate = event.getEventStartDate();
        this.eventEndDate = event.getEventEndDate();
        this.maxParticipant = form.getMaxParticipants();
        this.eventDescription = event.getEventDescription();
        this.eventImages = event.getEventImages();
    }
}