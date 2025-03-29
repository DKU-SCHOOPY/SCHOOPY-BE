package com.schoopy.back.event.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class RegistEventRequestDto {
    @NotNull
    private String eventName;
    @NotNull
    private Date surveyStartDate;
    @NotNull
    private Date surveyEndDate;
    @NotNull
    private Date eventStartDate;
    @NotNull
    private Date eventEndDate;
    @NotNull
    private int maxParticipants;
    @NotNull
    private int currentParticipants;
    @NotNull
    private String eventDescription;

    private List<String> eventImages;
    private List<String> qrCodeImages;
}
