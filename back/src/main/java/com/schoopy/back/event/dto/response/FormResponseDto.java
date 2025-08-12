package com.schoopy.back.event.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.event.common.EventResponseCode;
import com.schoopy.back.event.common.EventResponseMessage;
import com.schoopy.back.global.dto.ResponseDto;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormResponseDto extends ResponseDto{
    private Long formId;
    private LocalDate surveyStartDate;
    private LocalDate surveyEndDate;
    private Integer maxParticipants;
    private Integer currentParticipants;
    private List<QuestionResponseDto> questions;
    private String qr_toss_o; // form
    private String qr_toss_x; // form
    private String qr_kakaopay_o; // form
    private String qr_kakaopay_x; // form

    public static ResponseEntity<ResponseDto> formNotFound() {
        ResponseDto responseBody = new ResponseDto(EventResponseCode.NOT_FOUND, EventResponseMessage.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> getFail() {
        ResponseDto responseBody = new ResponseDto(EventResponseCode.GET_FAIL, EventResponseMessage.GET_FAIL);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
}
