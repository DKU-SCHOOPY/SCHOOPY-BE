package com.schoopy.back.event.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class RegistEventRequestDto {
    @NotNull
    private String eventName; // 1-1
    @NotNull
    private String department;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate surveyStartDate;

    private LocalDate surveyEndDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventEndDate;

    private int maxParticipants;
    private int currentParticipants;
    private String eventDescription; // 1-2
    private List<MultipartFile> eventImages;
    private List<String> qrCodeImages;
}