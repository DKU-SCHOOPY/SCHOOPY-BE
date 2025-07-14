package com.schoopy.back.home.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GetEventInformationRequestDto {

    @NotBlank
    private Long eventCode;
}
