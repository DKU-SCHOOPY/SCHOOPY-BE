package com.schoopy.back.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignInRequestDto {

    @NotBlank
    private String studentNum;

    @NotBlank
    private String password;

    @NotBlank 
    private String fcmToken;
}
