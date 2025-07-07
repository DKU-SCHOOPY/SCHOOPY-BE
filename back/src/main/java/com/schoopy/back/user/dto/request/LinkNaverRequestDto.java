package com.schoopy.back.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LinkNaverRequestDto {
    
    @NotBlank
    private String studentNum;  

    @NotBlank
    private String code;

    @NotBlank
    private String state;
}
