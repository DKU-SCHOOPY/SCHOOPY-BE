package com.schoopy.back.home.dto.response;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.event.entity.EventEntity;
import com.schoopy.back.global.dto.ResponseDto;

import lombok.Getter;

@Getter
public class GetEventInformationResponseDto extends ResponseDto {

    private Long eventCode;
    private String eventName;
    private String department;
    private String eventDescription;
    private List<String> eventImages;

    
    private GetEventInformationResponseDto(EventEntity event) {
        super();
        this.eventCode = event.getEventCode();
        this.eventName = event.getEventName();
        this.department = event.getDepartment();
        this.eventDescription = event.getEventDescription();
        this.eventImages = event.getEventImages();
    }

    public static ResponseEntity<GetEventInformationResponseDto> success(EventEntity event) {
        GetEventInformationResponseDto responseBody = new GetEventInformationResponseDto(event);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    } 

}
