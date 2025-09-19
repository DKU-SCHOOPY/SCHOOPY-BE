package com.schoopy.back.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckDepartmentForCouncilRequestDto {
    @NotBlank
    private String studentNum;
}
