package com.schoopy.back.event.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RemitRequestDto {
    @NotBlank
    private String eventCode;
}
