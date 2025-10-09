package com.schoopy.back.event.dto.request;

import com.google.firebase.database.annotations.NotNull;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationStatusRequestDto {
    @NotNull
    private Long eventCode;

    @NotBlank
    private String studentNum;
}