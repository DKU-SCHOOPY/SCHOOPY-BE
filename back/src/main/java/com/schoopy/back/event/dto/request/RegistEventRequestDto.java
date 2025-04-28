package com.schoopy.back.event.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class RegistEventRequestDto {
    @NotNull
    private String eventName; // 1-1
    @NotNull
    private String department;
    private Date surveyStartDate; //
    private Date surveyEndDate;
    private Date eventStartDate;
    private Date eventEndDate;
    private int maxParticipants;
    private int currentParticipants;
    private String eventDescription; // 1-2
    private List<MultipartFile> eventImages;
    private List<String> qrCodeImages;
}



