package com.schoopy.back.event.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RegistEventRequestDto {
    //EventEntity에 저장
    @NotNull
    private String eventName; // 1
    @NotNull
    private String department; // 1
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventStartDate; // 2
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventEndDate; // 2
    private String eventDescription; // 1
    private List<MultipartFile> eventImages; // 1

    //FormEntity에 저장
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate surveyStartDate; // 2
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate surveyEndDate; // 2
    private int maxParticipants; // 2
    private int currentParticipants;
    private String qr_toss_o;
    private String qr_toss_x;
    private String qr_kakaopay_o;
    private String qr_kakaopay_x;

    private List<QuestionDto> questions;

    @Getter
    @Setter
    public static class QuestionDto{
        private String questionText;
        private String questionType;
        private List<String> choices;
        private boolean isRequired;
        private boolean isMultiple;
    }
}