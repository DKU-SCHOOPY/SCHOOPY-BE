package com.schoopy.back.home.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import com.schoopy.back.event.entity.EventEntity;
import com.schoopy.back.event.entity.FormEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetHomeResponseDto {

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

    public static GetHomeResponseDto from(EventEntity event, FormEntity form) {
        GetHomeResponseDto dto = new GetHomeResponseDto();
        dto.setEventCode(event.getEventCode());
        dto.setEventName(event.getEventName());
        dto.setDepartment(event.getDepartment());
        dto.setServeyStartDate(form.getSurveyStartDate());
        dto.setServeyEndDate(form.getSurveyEndDate());
        dto.setEventStartDate(event.getEventStartDate());
        dto.setEventEndDate(event.getEventEndDate());
        dto.setMaxParticipant(form.getMaxParticipants());
        dto.setEventDescription(event.getEventDescription());
        dto.setEventImages(event.getEventImages()); // 필드 타입(List<String>)에 맞게 유지
        return dto;
    }
}