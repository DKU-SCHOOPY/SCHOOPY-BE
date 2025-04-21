package com.schoopy.back.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {
    @NotBlank
    @Pattern(regexp = "\\d{8}", message = "학번은 8자리 숫자여야 합니다.")
    private String studentNum;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{12,18}$")
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String certificationNumber;

    @NotBlank
    private String department;

    @NotBlank
    private String gender;

    @NotBlank
    private String birthDay;

    @NotBlank 
    private String phoneNum;
}
