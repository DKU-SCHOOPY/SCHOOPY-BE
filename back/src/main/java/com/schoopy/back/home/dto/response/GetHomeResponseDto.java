package com.schoopy.back.home.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.event.entity.EventEntity;
import com.schoopy.back.event.entity.FormEntity;
import com.schoopy.back.global.dto.ResponseDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetHomeResponseDto extends ResponseDto{

    private int noticeCount;

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

    private GetHomeResponseDto (EventEntity event, FormEntity form) {
        super();
        this.setEventCode(event.getEventCode());
        this.setEventName(event.getEventName());
        this.setDepartment(event.getDepartment());
        this.setServeyStartDate(form.getSurveyStartDate());
        this.setServeyEndDate(form.getSurveyEndDate());
        this.setEventStartDate(event.getEventStartDate());
        this.setEventEndDate(event.getEventEndDate());
        this.setMaxParticipant(form.getMaxParticipants());
        this.setEventDescription(event.getEventDescription());
        this.setEventImages(event.getEventImages()); // 필드 타입(List<String>)에 맞게 유지
    }

    private GetHomeResponseDto(EventEntity event) {
        super();
        this.setEventCode(event.getEventCode());
        this.setEventName(event.getEventName());
        this.setDepartment(event.getDepartment());
        this.setServeyStartDate(null);
        this.setServeyEndDate(null);
        this.setEventStartDate(event.getEventStartDate());
        this.setEventEndDate(event.getEventEndDate());
        this.setMaxParticipant(0);
        this.setEventDescription(event.getEventDescription());
        this.setEventImages(event.getEventImages()); // 필드 타입(List<String>)에 맞게 유지
    }

    public static ResponseEntity<GetHomeResponseDto> success(EventEntity event, FormEntity form) {
        GetHomeResponseDto responseBody = new GetHomeResponseDto(event, form);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    } 

    public static ResponseEntity<GetHomeResponseDto> success(EventEntity event) {
        GetHomeResponseDto responseBody = new GetHomeResponseDto(event);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static GetHomeResponseDto from(EventEntity event, FormEntity form) {
        return new GetHomeResponseDto(event, form);
    }

    public static GetHomeResponseDto from(EventEntity event) {
        return new GetHomeResponseDto(event);
    }

}