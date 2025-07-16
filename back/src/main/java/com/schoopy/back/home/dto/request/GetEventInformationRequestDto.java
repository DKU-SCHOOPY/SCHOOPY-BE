package com.schoopy.back.home.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GetEventInformationRequestDto {

    @NotNull
    private Long eventCode;
}
