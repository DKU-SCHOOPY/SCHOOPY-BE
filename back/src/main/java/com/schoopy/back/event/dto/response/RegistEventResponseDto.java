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

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistEventResponseDto extends ResponseDto{

    private Long eventCode;

    private String eventName;
    private String department;
    private LocalDate surveyStartDate;
    private LocalDate surveyEndDate;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
    private int maxParticipants;
    private int currentParticipants;
    @Column(columnDefinition = "LONGTEXT")
    private String eventDescription;
    @ElementCollection
    private List<String> eventImages;
    private String qr_toss_o;
    private String qr_toss_x;
    private String qr_kakaopay_o;
    private String qr_kakaopay_x;

    public static ResponseEntity<RegistEventResponseDto> success(
            Long eventCode, String eventName, String department, LocalDate surveyStartDate, LocalDate surveyEndDate,
            LocalDate eventStartDate, LocalDate eventEndDate, int maxParticipants, int currentParticipants,
            String eventDescription, List<String> eventImages,
            String qr_toss_o, String qr_toss_x, String qr_kakaopay_o, String qr_kakaopay_x
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
        responseBody.setQr_toss_o(qr_toss_o);
        responseBody.setQr_toss_x(qr_toss_x);
        responseBody.setQr_kakaopay_o(qr_kakaopay_o);
        responseBody.setQr_kakaopay_x(qr_kakaopay_x);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    public static ResponseEntity<ResponseDto> registFail() {
        ResponseDto responseBody = new ResponseDto(EventResponseCode.REGIST_FAIL, EventResponseMessage.REGIST_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }
}