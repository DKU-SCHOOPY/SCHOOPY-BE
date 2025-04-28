package com.schoopy.back.event.dto.response;

import com.schoopy.back.event.common.EventResponseCode;
import com.schoopy.back.event.common.EventResponseMessage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistEventResponseDto extends ResponseDto{

    private Long eventCode;

    private String eventName;
    private String department;
    private Date surveyStartDate;
    private Date surveyEndDate;
    private Date eventStartDate;
    private Date eventEndDate;
    private int maxParticipants;
    private int currentParticipants;
    @Column(columnDefinition = "LONGTEXT")
    private String eventDescription;
    @ElementCollection
    private List<String> eventImages;
    @ElementCollection
    private List<String> QrCodeImages;

    public static ResponseEntity<RegistEventResponseDto> success(
            Long eventCode, String eventName, String department, Date surveyStartDate, Date surveyEndDate, Date eventStartDate, Date eventEndDate,
            int maxParticipants, int currentParticipants, String eventDescription, List<String> eventImages, List<String> QrCodeImages
    ) {
        RegistEventResponseDto responseBody = new RegistEventResponseDto();
        responseBody.setEventCode(eventCode);
        responseBody.setEventName(eventName);
        responseBody.setDepartment(department);
        responseBody.setSurveyStartDate(surveyStartDate);
        responseBody.setSurveyEndDate(surveyEndDate);
        responseBody.setEventStartDate(eventStartDate);
        responseBody.setEventEndDate(eventEndDate);
        responseBody.setMaxParticipants(maxParticipants);
        responseBody.setCurrentParticipants(currentParticipants);
        responseBody.setEventDescription(eventDescription);
        responseBody.setEventImages(eventImages);
        responseBody.setQrCodeImages(QrCodeImages);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    public static ResponseEntity<ResponseDto> registFail() {
        ResponseDto responseBody = new ResponseDto(EventResponseCode.REGIST_FAIL, EventResponseMessage.REGIST_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }
}
