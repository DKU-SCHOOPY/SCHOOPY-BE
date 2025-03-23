package com.schoopy.back.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckCertificationRequestDto {
    @NotBlank
    @Pattern(regexp = "\\d{8}", message = "학번은 8자리 숫자여야 합니다.")
    private String studentNum;

    @NotBlank
    private String certificationNumber;
}
