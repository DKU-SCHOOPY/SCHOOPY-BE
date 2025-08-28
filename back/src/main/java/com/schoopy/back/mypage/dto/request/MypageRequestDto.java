package com.schoopy.back.mypage.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MypageRequestDto {

    @NotBlank
    private String studentNum;
}
